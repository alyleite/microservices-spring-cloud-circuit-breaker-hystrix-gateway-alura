package br.com.alura.microservice.transportador.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntregaDTO {

    private Long pedidoId;

    private LocalDate dataParaEntrega;

    private String enderecoOrigem;

    private String enderecoDestino;

}
