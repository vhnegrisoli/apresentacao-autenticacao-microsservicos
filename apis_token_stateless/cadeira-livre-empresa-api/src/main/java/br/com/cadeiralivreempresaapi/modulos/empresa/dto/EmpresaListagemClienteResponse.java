package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

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
public class EmpresaListagemClienteResponse {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String tipoEmpresa;

    public static EmpresaListagemClienteResponse of(Empresa empresa) {
        var response = new EmpresaListagemClienteResponse();
        BeanUtils.copyProperties(empresa, response);
        response.setTipoEmpresa(empresa.getTipoEmpresa().getTipoEmpresa());
        return response;
    }
}
