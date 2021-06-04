package br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequest {

    private String descricao;
    private Double preco;
    private Integer empresaId;
}
