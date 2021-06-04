package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRequest {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String razaoSocial;
    private ETipoEmpresa tipoEmpresa;
    private Integer tempoRefreshCadeiraLivre;
    private List<EnderecoRequest> enderecos;
}
