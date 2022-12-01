package br.com.loja.client;

import br.com.loja.dto.InfoEntregaDTO;
import br.com.loja.dto.VoucherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("transportador")
public interface TransportadorClient {

    @RequestMapping(method = RequestMethod.POST, value = "/entrega")
    VoucherDTO reservaEntrega(InfoEntregaDTO entregaDTO);
}
