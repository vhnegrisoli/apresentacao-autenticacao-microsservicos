package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

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
public class EnderecoResponse {

    private Integer id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numeroRua;
    private String cep;
    private String complemento;

    public static EnderecoResponse converterDe(Endereco endereco) {
        var response = new EnderecoResponse();
        BeanUtils.copyProperties(endereco, response);
        return response;
    }
}
