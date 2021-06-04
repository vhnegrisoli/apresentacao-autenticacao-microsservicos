package br.com.cadeiralivreempresaapi.modulos.jwt.model;

import br.com.cadeiralivreempresaapi.modulos.jwt.dto.UsuarioTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO_LOGIN_JWT")
public class UsuarioLoginJwt {

    @Id
    @Column(name = "USUARIO_ID")
    private String usuarioId;

    @Column(name = "JWT", nullable = false, unique = true, length = 500)
    private String jwt;

    @Column(name = "TOKEN_VALIDA", nullable = false)
    private boolean tokenValida;

    @Column(name = "ULTIMA_ATUALIZACAO", nullable = false)
    private LocalDateTime ultimaAtualizacao;

    public static UsuarioLoginJwt gerarUsuario(UsuarioTokenResponse response, boolean valida) {
        return UsuarioLoginJwt
            .builder()
            .usuarioId(response.getUsuarioId())
            .jwt(response.getToken())
            .tokenValida(valida)
            .ultimaAtualizacao(LocalDateTime.now())
            .build();
    }
}
