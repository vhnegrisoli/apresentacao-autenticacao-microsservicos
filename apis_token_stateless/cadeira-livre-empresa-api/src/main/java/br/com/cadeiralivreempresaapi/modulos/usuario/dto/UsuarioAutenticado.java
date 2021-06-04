package br.com.cadeiralivreempresaapi.modulos.usuario.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESexo;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Permissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.PatternUtil.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAutenticado {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private ESexo sexo;
    private List<String> permissoes;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime ultimoAcesso;
    private ESituacaoUsuario situacao;

    public boolean isAdmin() {
        return permissoes.contains(EPermissao.ADMIN.name());
    }

    public boolean isSocioOuProprietario() {
        return permissoes.contains(EPermissao.SOCIO.name()) || permissoes.contains(EPermissao.PROPRIETARIO.name());
    }

    public boolean isFuncionario() {
        return permissoes.contains(EPermissao.FUNCIONARIO.name());
    }

    public static UsuarioAutenticado of(Usuario usuario) {
        var usuarioAutenticado = new UsuarioAutenticado();
        BeanUtils.copyProperties(usuario, usuarioAutenticado);
        usuarioAutenticado.setPermissoes(usuario
            .getPermissoes()
            .stream()
            .map(Permissao::getPermissao)
            .map(EPermissao::name)
            .collect(Collectors.toList()));
        return usuarioAutenticado;
    }

    public static UsuarioAutenticado of(UserDetails userDetails) {
        var usuarioAutenticado = new UsuarioAutenticado();
        BeanUtils.copyProperties(userDetails, usuarioAutenticado);
        usuarioAutenticado.setPermissoes(
            userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
                .map(permissao -> permissao.replace("ROLE_", ""))
            .collect(Collectors.toList()));
        return usuarioAutenticado;
    }
}
