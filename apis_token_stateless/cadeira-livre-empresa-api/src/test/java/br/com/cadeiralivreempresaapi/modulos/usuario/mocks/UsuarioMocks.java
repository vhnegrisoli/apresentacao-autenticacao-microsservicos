package br.com.cadeiralivreempresaapi.modulos.usuario.mocks;

import br.com.cadeiralivreempresaapi.config.auth.UserDetailsImpl;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioRequest;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESexo;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Permissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMocks {

    public static UsuarioAutenticado umUsuarioAutenticadoAdmin() {
        return UsuarioAutenticado
            .builder()
            .cpf("875.604.250-70")
            .nome("Admin")
            .email("admin@gmail.com")
            .id(1)
            .permissoes(List.of(EPermissao.ADMIN.name()))
            .sexo(ESexo.MASCULINO)
            .situacao(ESituacaoUsuario.ATIVO)
            .ultimoAcesso(LocalDateTime.now())
            .build();
    }

    public static UsuarioAutenticado umUsuarioAutenticadoProprietario() {
        return UsuarioAutenticado
            .builder()
            .cpf("932.249.970-28")
            .nome("Proprietário")
            .email("proprietario@gmail.com")
            .id(1)
            .permissoes(List.of(EPermissao.PROPRIETARIO.name()))
            .sexo(ESexo.MASCULINO)
            .situacao(ESituacaoUsuario.ATIVO)
            .ultimoAcesso(LocalDateTime.now())
            .build();
    }

    public static UsuarioAutenticado umUsuarioAutenticadoSocio() {
        return UsuarioAutenticado
            .builder()
            .cpf("668.258.660-44")
            .nome("Usuário Sócio")
            .email("socio@gmail.com")
            .id(1)
            .permissoes(List.of(EPermissao.SOCIO.name()))
            .sexo(ESexo.MASCULINO)
            .situacao(ESituacaoUsuario.ATIVO)
            .ultimoAcesso(LocalDateTime.now())
            .build();
    }

    public static UsuarioAutenticado umUsuarioAutenticadoFuncionario() {
        return UsuarioAutenticado
            .builder()
            .cpf("836.746.870-89")
            .nome("Funcionário")
            .email("funcionario@gmail.com")
            .id(1)
            .permissoes(List.of(EPermissao.FUNCIONARIO.name()))
            .sexo(ESexo.MASCULINO)
            .situacao(ESituacaoUsuario.ATIVO)
            .ultimoAcesso(LocalDateTime.now())
            .build();
    }

    public static Usuario umUsuario() {
        return Usuario
            .builder()
            .id(1)
            .cpf("332.368.250-57")
            .dataCadastro(LocalDateTime.now())
            .dataNascimento(LocalDate.parse("1998-01-01"))
            .email("usuario@gmail.com")
            .permissoes(Collections.singleton(umaPermissaoAdmin()))
            .nome("Usuario")
            .senha("123456")
            .sexo(ESexo.MASCULINO)
            .tokenNotificacao("123456")
            .situacao(ESituacaoUsuario.ATIVO)
            .ultimoAcesso(LocalDateTime.now())
            .build();
    }

    public static Permissao umaPermissaoAdmin() {
        return Permissao
            .builder()
            .id(EPermissao.ADMIN.getId())
            .permissao(EPermissao.ADMIN)
            .descricao(EPermissao.ADMIN.getDescricao())
            .build();
    }

    public static Permissao umaPermissaoProprietario() {
        return Permissao
            .builder()
            .id(EPermissao.PROPRIETARIO.getId())
            .permissao(EPermissao.PROPRIETARIO)
            .descricao(EPermissao.PROPRIETARIO.getDescricao())
            .build();
    }

    public static Permissao umaPermissaoSocio() {
        return Permissao
            .builder()
            .id(EPermissao.SOCIO.getId())
            .permissao(EPermissao.SOCIO)
            .descricao(EPermissao.SOCIO.getDescricao())
            .build();
    }

    public static Permissao umaPermissaoFuncionario() {
        return Permissao
            .builder()
            .id(EPermissao.FUNCIONARIO.getId())
            .permissao(EPermissao.FUNCIONARIO)
            .descricao(EPermissao.FUNCIONARIO.getDescricao())
            .build();
    }

    public static UsuarioRequest umUsuarioRequest() {
        return UsuarioRequest
            .builder()
            .id(1)
            .cpf("332.368.250-57")
            .dataNascimento(LocalDate.parse("1998-01-01"))
            .email("usuario@gmail.com")
            .nome("Usuario")
            .senha("123456")
            .sexo(ESexo.MASCULINO)
            .build();
    }

    public static UserDetailsImpl umUserDetails() {
        var usuario = umUsuario();
        return new UserDetailsImpl(usuario, usuario
            .getPermissoes()
            .stream()
            .map(permissao -> "ROLE_" + permissao.getPermissao())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
    }
}
