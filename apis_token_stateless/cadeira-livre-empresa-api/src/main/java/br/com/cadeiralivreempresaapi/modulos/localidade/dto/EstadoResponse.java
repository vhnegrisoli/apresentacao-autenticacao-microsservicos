package br.com.cadeiralivreempresaapi.modulos.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoResponse {

    private Integer id;
    private String sigla;
    private String nome;
}
