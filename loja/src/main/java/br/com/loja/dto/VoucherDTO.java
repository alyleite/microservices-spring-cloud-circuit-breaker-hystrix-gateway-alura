package br.com.loja.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherDTO {


    private Long numero;

    private LocalDate previsaoParaEntrega;
}
