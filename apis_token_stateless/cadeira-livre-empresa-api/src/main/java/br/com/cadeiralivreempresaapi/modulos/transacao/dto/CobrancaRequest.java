package br.com.cadeiralivreempresaapi.modulos.transacao.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CobrancaRequest {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private EnderecoCobrancaRequest endereco;

    public static CobrancaRequest converterDe(Empresa empresa, Endereco endereco) {
        return CobrancaRequest
            .builder()
            .id(empresa.getId())
            .nome(isEmpty(empresa.getRazaoSocial()) ? empresa.getNome() : empresa.getRazaoSocial())
            .cpfCnpj(empresa.getCpfCnpj())
            .endereco(EnderecoCobrancaRequest.converterDe(endereco))
            .build();
    }
}
