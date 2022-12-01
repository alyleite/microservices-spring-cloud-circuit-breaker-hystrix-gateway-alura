package br.com.loja.service;

import br.com.loja.client.FornecedorClient;
import br.com.loja.client.TransportadorClient;
import br.com.loja.config.FeignClientException;
import br.com.loja.dto.*;
import br.com.loja.model.Compra;
import br.com.loja.model.CompraStatus;
import br.com.loja.repository.CompraRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final FornecedorClient fornecedorClient;
    private final TransportadorClient transportadorClient;
    private final CompraRepository compraRepository;

    @HystrixCommand(threadPoolKey = "getByIdFallbackThreadPoll")
    @Override
    public Compra getById(Long id) {
        return compraRepository.findById(id).orElse(new Compra());
    }

    @HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraFallbackThreadPoll")
    @Override
    public Compra realizaCompra(CompraDTO compra) {
        try {
            var compraSalva = Compra.builder().status(CompraStatus.RECEBIDO).build();
            compraSalva.setEnderecoDestino(compra.getEndereco().toString());
            log.info("Salvando a compra: " + compraSalva.getStatus());
            compraRepository.save(compraSalva);
            compra.setCompraId(compraSalva.getId());

            String estado = compra.getEndereco().getEstado();
            log.info("Buscando informações do fornecedor de {}", estado);
            InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(estado);

            log.info("Realizando o pedido");
            InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
            compraSalva.setStatus(CompraStatus.PEDIDO_REALIZADO);
            compraSalva.setPedidoId(pedido.getId());
            compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
            compraRepository.save(compraSalva);

            if(true)  throw new RuntimeException();

            log.info("Reservando a entrega");
            InfoEntregaDTO entregaDTO = InfoEntregaDTO.builder()
                    .pedidoId(pedido.getId())
                    .dataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()))
                    .enderecoOrigem(info.getEndereco())
                    .enderecoDestino(compra.getEndereco().toString())
                    .build();

            VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDTO);
            compraSalva.setStatus(CompraStatus.RESERVA_ENTREGA_REALIZADA);
            compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
            compraSalva.setVoucher(voucher.getNumero());
            log.info("Salvando a compra: " + compraSalva.getStatus());
            compraRepository.save(compraSalva);

            return compraSalva;
        } catch (FeignClientException e) {
            if (!Integer.valueOf(HttpStatus.NOT_FOUND.value()).equals(e.getStatus())) {
                log.error("Error: " + e.getErrorMessage());
            }
            throw e;
        }
    }

    public Compra realizaCompraFallback(CompraDTO compra) {
        if(compra.getCompraId() != null) {
            return compraRepository.findById(compra.getCompraId()).orElse(null);
        }
        return Compra.builder()
                .enderecoDestino(compra.getEndereco().toString())
                .build();
    }
}
