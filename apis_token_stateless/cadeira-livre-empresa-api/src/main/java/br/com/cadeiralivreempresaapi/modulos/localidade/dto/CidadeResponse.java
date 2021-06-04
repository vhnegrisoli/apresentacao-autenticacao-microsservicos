package br.com.cadeiralivreempresaapi.modulos.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadeResponse {

    private Long id;
    private String nome;
}
