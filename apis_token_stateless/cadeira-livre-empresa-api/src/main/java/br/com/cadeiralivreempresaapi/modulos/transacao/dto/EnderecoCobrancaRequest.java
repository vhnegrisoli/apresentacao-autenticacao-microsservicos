package br.com.cadeiralivreempresaapi.modulos.transacao.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCobrancaRequest {

    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numeroRua;
    private String complemento;
    private String cep;

    public static EnderecoCobrancaRequest converterDe(Endereco endereco) {
        var request = new EnderecoCobrancaRequest();
        BeanUtils.copyProperties(endereco, request);
        return request;
    }
}
