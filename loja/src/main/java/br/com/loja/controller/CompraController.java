package br.com.loja.controller;

import br.com.loja.dto.CompraDTO;
import br.com.loja.model.Compra;
import br.com.loja.service.CompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
@Slf4j
@RequiredArgsConstructor
@EnableEurekaClient
public class CompraController {
    private final CompraService compraService;

    @GetMapping("/{id}")
    public Compra getById(@PathVariable("id") Long id) {
        return this.compraService.getById(id);
    }

    @PostMapping()
    public Compra realizaCompra(@RequestBody CompraDTO dto) {
        return this.compraService.realizaCompra(dto);
    }
}
