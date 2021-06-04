package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCobrancaRequest {

    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numeroRua;
    private String complemento;
    private String cep;
}
