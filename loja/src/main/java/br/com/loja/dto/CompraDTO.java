package br.com.loja.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompraDTO {

    @JsonIgnore
    private Long compraId;

    private List<ItemDTO> itens;
    private EnderecoDTO endereco;
}
