package br.com.cadeiralivreempresaapi.config.auth;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESexo;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class UserDetailsImpl extends User {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private ESexo sexo;
    private LocalDateTime ultimoAcesso;

    public UserDetailsImpl(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this(usuario, true, true, true, true, authorities);
    }

    public UserDetailsImpl(Usuario usuario, boolean enabled, boolean accountNonExpired,
                           boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.sexo = usuario.getSexo();
        this.ultimoAcesso = usuario.getUltimoAcesso();
    }
}
