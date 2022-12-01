package br.com.alura.microservice.transportador.model;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "entrega")
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long pedidoId;

	private LocalDate dataParaBusca;

	private LocalDate previsaoParaEntrega;

	private String enderecoOrigem;

	private String enderecoDestino;
}
