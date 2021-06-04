package br.com.cadeiralivreempresaapi.modulos.localidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CepResponse {

    private String cep;
    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public static CepResponse converterDe(ViaCepResponse viaCepResponse) {
        return CepResponse
            .builder()
            .cep(viaCepResponse.getCep())
            .rua(viaCepResponse.getLogradouro())
            .complemento(viaCepResponse.getComplemento())
            .bairro(viaCepResponse.getBairro())
            .cidade(viaCepResponse.getLocalidade())
            .estado(viaCepResponse.getUf())
            .build();
    }
}
