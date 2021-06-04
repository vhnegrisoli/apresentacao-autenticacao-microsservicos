package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.PAIS_BRASIL;
import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.TIPO_INDIVIDUO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteClientRequest {

    @JsonProperty("external_id")
    private String idExterno;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("type")
    private String tipo;

    @JsonProperty("country")
    private String pais;

    private String email;

    @JsonProperty("documents")
    private List<ClienteDocumentoClientRequest> documentos;

    @JsonProperty("phone_numbers")
    private List<String> numerosTelefone;

    public static ClienteClientRequest converterDe(JwtUsuarioResponse usuario, List<String> numerosTelefone) {
        return ClienteClientRequest
            .builder()
            .idExterno(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .documentos(Collections.singletonList(ClienteDocumentoClientRequest.converterDe(usuario.getCpf())))
            .tipo(TIPO_INDIVIDUO)
            .pais(PAIS_BRASIL)
            .numerosTelefone(numerosTelefone)
            .build();
    }
}
