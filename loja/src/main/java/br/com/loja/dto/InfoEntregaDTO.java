package br.com.loja.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoEntregaDTO {
    private Long pedidoId;

    private LocalDate dataParaEntrega;

    private String enderecoOrigem;

    private String enderecoDestino;

}
