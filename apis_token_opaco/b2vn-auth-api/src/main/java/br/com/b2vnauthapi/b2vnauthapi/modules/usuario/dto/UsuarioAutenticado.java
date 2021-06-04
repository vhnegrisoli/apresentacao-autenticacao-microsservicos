package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.dto;

import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.enums.EPermissao;
import br.com.b2vnauthapi.b2vnauthapi.modules.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static br.com.b2vnauthapi.b2vnauthapi.modules.usuario.enums.EPermissao.ADMIN;
import static br.com.b2vnauthapi.b2vnauthapi.modules.usuario.enums.EPermissao.USER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAutenticado {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private EPermissao permissao;
    private String descricao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime ultimoAcesso;

    public boolean isAdmin() {
        return permissao.equals(ADMIN);
    }

    public boolean isUser() {
        return permissao.equals(USER);
    }

    public static UsuarioAutenticado of(Usuario usuario) {
        var usuarioAutenticado = new UsuarioAutenticado();
        BeanUtils.copyProperties(usuario, usuarioAutenticado);
        usuarioAutenticado.setPermissao(usuario.getPermissao().getCodigo());
        usuarioAutenticado.setDescricao(usuario.getPermissao().getDescricao());
        return usuarioAutenticado;
    }
}
