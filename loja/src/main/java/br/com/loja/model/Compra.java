package br.com.loja.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;

    private Integer tempoDePreparo;

    private String enderecoDestino;

    private LocalDate dataParaEntrega;

    private Long voucher;

    @Enumerated(EnumType.STRING)
    private CompraStatus status;

}
