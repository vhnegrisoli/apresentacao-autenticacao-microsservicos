package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaPageResponse {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private ETipoEmpresa tipoEmpresa;
    private ESituacaoEmpresa situacao;
    private Integer tempoRefreshCadeiraLivre;

    public static EmpresaPageResponse of(Empresa empresa) {
        var response = new EmpresaPageResponse();
        BeanUtils.copyProperties(empresa, response);
        return response;
    }
}
