package br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoAgendaResponse {

    private Integer servicoId;
    private String descricao;
    private Double preco;

    public static ServicoAgendaResponse of(Servico servico) {
        return ServicoAgendaResponse
            .builder()
            .servicoId(servico.getId())
            .descricao(servico.getDescricao())
            .preco(servico.getPreco())
            .build();
    }
}
