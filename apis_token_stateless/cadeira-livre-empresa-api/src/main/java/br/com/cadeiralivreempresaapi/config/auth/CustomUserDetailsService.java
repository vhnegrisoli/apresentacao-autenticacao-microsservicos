package br.com.cadeiralivreempresaapi.config.auth;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.usuario.messages.UsuarioMessages.ACESSO_INVALIDO;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws ValidacaoException {
        return usuarioRepository
            .findByEmailAndSituacao(email, ESituacaoUsuario.ATIVO)
            .map(usuario -> new UserDetailsImpl(
                usuario,
                getPermissoes(usuario)))
            .orElseThrow(() -> ACESSO_INVALIDO);
    }

    private List<SimpleGrantedAuthority> getPermissoes(Usuario usuario) {
        return usuario
            .getPermissoes()
            .stream()
            .map(permissao -> "ROLE_" + permissao.getPermissao())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}