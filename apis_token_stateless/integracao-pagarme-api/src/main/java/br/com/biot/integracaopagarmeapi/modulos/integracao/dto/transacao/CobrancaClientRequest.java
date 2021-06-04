package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.CobrancaRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CobrancaClientRequest {

    @JsonProperty("name")
    private String nome;

    @JsonProperty("address")
    private EnderecoCobrancaClientRequest endereco;

    public static CobrancaClientRequest converterDe(CobrancaRequest cobrancaRequest) {
        return CobrancaClientRequest
            .builder()
            .nome(cobrancaRequest.getNome())
            .endereco(EnderecoCobrancaClientRequest.converterDe(cobrancaRequest.getEndereco()))
            .build();
    }
}
