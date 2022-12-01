package br.com.alura.microservice.transportador.controller;

import br.com.alura.microservice.transportador.dto.EntregaDTO;
import br.com.alura.microservice.transportador.dto.VoucherDTO;
import br.com.alura.microservice.transportador.service.EntregaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrega")
@AllArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    public VoucherDTO reservaEntrega(@RequestBody EntregaDTO pedidoDTO) {
        return entregaService.reservaEntrega(pedidoDTO);
    }
}
