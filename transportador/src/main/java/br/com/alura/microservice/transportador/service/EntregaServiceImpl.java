package br.com.alura.microservice.transportador.service;

import br.com.alura.microservice.transportador.dto.EntregaDTO;
import br.com.alura.microservice.transportador.dto.VoucherDTO;
import br.com.alura.microservice.transportador.model.Entrega;
import br.com.alura.microservice.transportador.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository repository;

    @Override
    public VoucherDTO reservaEntrega(EntregaDTO pedidoDTO) {

        Entrega entrega = Entrega.builder()
                .dataParaBusca(pedidoDTO.getDataParaEntrega())
                .previsaoParaEntrega(pedidoDTO.getDataParaEntrega().plusDays(1L))
                .enderecoDestino(pedidoDTO.getEnderecoDestino())
                .enderecoOrigem(pedidoDTO.getEnderecoOrigem())
                .pedidoId(pedidoDTO.getPedidoId())
                .build();

        repository.save(entrega);

        return VoucherDTO.builder()
                .numero(entrega.getId())
                .previsaoParaEntrega(entrega.getPrevisaoParaEntrega())
                .build();
    }

}
