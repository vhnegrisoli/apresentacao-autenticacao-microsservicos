package br.com.cadeiralivreempresaapi.modulos.jwt.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.jwt.repository.UsuarioLoginJwtRepository;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.umUsuarioLoginJwt;
import static br.com.cadeiralivreempresaapi.modulos.jwt.mocks.JwtMocks.umUsuarioTokenResponse;
import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenExpirado;
import static br.com.cadeiralivreempresaapi.modulos.jwt.util.JwtTestUtil.gerarTokenTeste;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuarioAutenticadoAdmin;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuarioAutenticadoFuncionario;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(JwtService.class)
@ExtendWith(MockitoExtension.class)
public class JwtServiceIntegrationTest {

    private static final String JWT = gerarTokenTeste();

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioLoginJwtRepository usuarioLoginJwtRepository;
    @MockBean
    private AutenticacaoService autenticacaoService;

    @Test
    @DisplayName("Deve retornar True quando token jwt for válido")
    public void verificarTokenValida_deveRetornarTrue_quandoTokenForValido() {
        assertThat(jwtService.verificarTokenValida(JWT)).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar validar token quando token jwt for inválido")
    public void verificarTokenValida_deveLancarException_quandoTokenForInvalido() {
        assertThat(jwtService.verificarTokenValida(JWT + "12")).isFalse();
    }

    @Test
    @DisplayName("Deve retornar claims com usuário quando token estiver correto")
    public void descriptografarJwt_deveRetornarClaimsComUsuario_quandoTokenEstiverCorreto() {
        var claims = jwtService.descriptografarJwt(JWT);
        assertThat(claims).isNotNull();
        assertThat(claims.getBody().get("id")).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(claims.getBody().get("nome")).isEqualTo("Victor Hugo Negrisoli");
        assertThat(claims.getBody().get("email")).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(claims.getBody().get("cpf")).isEqualTo("103.324.589-54");
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar descriptografar quando token jwt for inválido")
    public void descriptografarJwt_deveLancarException_quandoTokenForInvalido() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> jwtService.descriptografarJwt(JWT + "12"))
            .withMessage("Erro ao tentar descriptografar o token.");
    }

    @Test
    @DisplayName("Deve recuperar usuario response quando informar token válido")
    public void recuperarDadosDoUsuarioDoToken_deveRecuperarUsuario_quandoInformarTokenValido() {
        var usuario = jwtService.recuperarDadosDoUsuarioDoToken(JWT);
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo("5cd48099-1009-43c4-b979-f68148a2a81d");
        assertThat(usuario.getNome()).isEqualTo("Victor Hugo Negrisoli");
        assertThat(usuario.getEmail()).isEqualTo("vhnegrisoli@gmail.com");
        assertThat(usuario.getCpf()).isEqualTo("103.324.589-54");
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar recuperar usuário quando token jwt for inválido")
    public void descriptografarJwt_deveRecuperarUsuario_quandoInformarTokenValido() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> jwtService.recuperarDadosDoUsuarioDoToken(JWT + "12"))
            .withMessage("O token está inválido.");
    }

    @Test
    @DisplayName("Deve retornar True quando usuário estiver com token válida")
    public void validarUsuarioLogado_deveRetornarTrue_quandoUsuarioExistirETokenEstiverValido() {
        usuarioLoginJwtRepository.save(umUsuarioLoginJwt(true));
        assertThat(jwtService.validarUsuarioLogado(JWT)).isTrue();
    }

    @Test
    @DisplayName("Deve retornar False quando usuário estiver com token for inválida")
    public void validarUsuarioLogado_deveRetornarFalse_quandoUsuarioExistirETokenEstiverInvalido() {
        usuarioLoginJwtRepository.save(umUsuarioLoginJwt(false));
        assertThat(jwtService.validarUsuarioLogado(JWT)).isFalse();
    }

    @Test
    @DisplayName("Deve criar usuário e retornar True quando usuário não existir")
    public void validarUsuarioLogado_deveCriarUsuarioERetornarTrue_quandoUsuarioNaoExistir() {
        usuarioLoginJwtRepository.deleteAll();
        assertThat(usuarioLoginJwtRepository.existsById("5cd48099-1009-43c4-b979-f68148a2a81d")).isFalse();
        assertThat(jwtService.validarUsuarioLogado(JWT)).isTrue();
        assertThat(usuarioLoginJwtRepository.findById("5cd48099-1009-43c4-b979-f68148a2a81d").get().isTokenValida()).isTrue();
    }

    @Test
    @DisplayName("Deve salvar usuario quando nao existir")
    public void salvarUsuarioDoToken_deveSalvarNovoUsuario_quandoInformarDados() {
        usuarioLoginJwtRepository.deleteAll();
        jwtService.salvarUsuarioDoToken(umUsuarioTokenResponse(), true);
        assertThat(usuarioLoginJwtRepository.findById("5cd48099-1009-43c4-b979-f68148a2a81d").get().isTokenValida()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar True quando usuário estiver logado com token válida")
    public void verificarUsuarioValidoComTokenValida_deveRetornarTrue_quandoUsuarioEstiverLogadoComTokenValida() {
        usuarioLoginJwtRepository.save(umUsuarioLoginJwt(true));
        assertThat(jwtService.verificarUsuarioValidoComTokenValida(JWT)).isTrue();
    }

    @Test
    @DisplayName("Deve retornar False quando usuário estiver com token inválida")
    public void verificarUsuarioValidoComTokenValida_deveRetornarFalse_quandoUsuarioEstiverComTokenInvalida() {
        assertThat(jwtService.verificarUsuarioValidoComTokenValida(JWT + "12")).isFalse();
    }

    @Test
    @DisplayName("Deve retornar False quando usuário estiver com token válida mas deslogado")
    public void verificarUsuarioValidoComTokenValida_deveRetornarFalse_quandoUsuarioEstiverComTokenValidaMasDeslogado() {
        usuarioLoginJwtRepository.save(umUsuarioLoginJwt(false));
        assertThat(jwtService.verificarUsuarioValidoComTokenValida(JWT)).isFalse();
    }

    @Test
    @DisplayName("Deve remover tokens inválidas via timer sem autenticação")
    public void removerTokensInvalidas_deveRemoverTokensInvalidas_quandoInformarSemAutenticacao() {
        var usuario = umUsuarioLoginJwt(true);
        usuario.setJwt(gerarTokenExpirado());
        usuarioLoginJwtRepository.save(usuario);
        assertThat(usuarioLoginJwtRepository.findAll().isEmpty()).isFalse();
        jwtService.removerTokensInvalidas(false);
        assertThat(usuarioLoginJwtRepository.findAll().isEmpty()).isTrue();

        verify(autenticacaoService, times(0)).getUsuarioAutenticado();
    }

    @Test
    @DisplayName("Deve remover tokens inválidas via request com autenticação")
    public void removerTokensInvalidas_deveRemoverTokensInvalidas_quandoInformarComAutenticacao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());
        var usuario = umUsuarioLoginJwt(true);
        usuario.setJwt(gerarTokenExpirado());
        usuarioLoginJwtRepository.save(usuario);
        assertThat(usuarioLoginJwtRepository.findAll().isEmpty()).isFalse();
        jwtService.removerTokensInvalidas(true);
        assertThat(usuarioLoginJwtRepository.findAll().isEmpty()).isTrue();

        verify(autenticacaoService, times(1)).getUsuarioAutenticado();
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar remover tokens inválidas via request sem possuir permissão")
    public void removerTokensInvalidas_deveLancarException_quandoPrecisarDeAutenticacaoEUsuarioNaoPossuir() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> jwtService.removerTokensInvalidas(true))
            .withMessage("Você não tem permissão para remover os JWTs do sistema.");

        verify(autenticacaoService, times(1)).getUsuarioAutenticado();
    }

    @Test
    @DisplayName("Deve fazer nada quando não existirem tokens para remover")
    public void removerTokensInvalidas_deveFazerNada_quandoNaoExistiremTokensParaRemover() {
        usuarioLoginJwtRepository.deleteAll();
        jwtService.removerTokensInvalidas(false);
    }
}