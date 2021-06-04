package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto;

import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String permissao;
    private String descricao;

    public static UsuarioResponse of(Usuario usuario) {
        var response = new UsuarioResponse();
        BeanUtils.copyProperties(usuario, response);
        response.setPermissao(usuario.getPermissao().getCodigo().toString());
        response.setDescricao(usuario.getPermissao().getDescricao());
        return response;
    }
}
