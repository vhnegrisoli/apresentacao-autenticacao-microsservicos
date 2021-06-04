package br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoResponse {

    private Integer id;
    private String descricao;
    private Double preco;
    private String empresa;
    private String cpfCnpj;

    public static ServicoResponse of(Servico servico) {
        var response = new ServicoResponse();
        BeanUtils.copyProperties(servico, response);
        response.setEmpresa(servico.getEmpresa().getNome());
        response.setCpfCnpj(servico.getEmpresa().getCpfCnpj());
        return response;
    }
}
