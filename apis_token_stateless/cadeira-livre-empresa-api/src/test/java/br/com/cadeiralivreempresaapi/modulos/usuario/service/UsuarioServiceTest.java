package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private PermissaoService permissaoService;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("Deve salvar usuário proprietário quando dados estiverem corretos")
    public void salvarProprietario_deveSalvarUsuarioProprietario_quandoDadosEstiveremCorretos() {
        when(permissaoService.buscarPorCodigo(any())).thenReturn(umaPermissaoProprietario());

        var request = umUsuarioRequest();
        var response = service.salvarProprietario(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Proprietário inserido com sucesso!");

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve salvar usuário sócio e enviar para service de empresa quando dados estiverem corretos")
    public void salvarSocio_deveSalvarUsuarioSocio_quandoDadosEstiveremCorretos() {
        when(permissaoService.buscarPorCodigo(any())).thenReturn(umaPermissaoSocio());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(umUsuario());

        var request = umUsuarioRequest();
        var response = service.salvarSocio(request, 1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Sócio inserido com sucesso!");

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(empresaService, times(1)).inserirSocio(any(Usuario.class), anyInt());
    }

    @Test
    @DisplayName("Deve salvar usuário funcionário e enviar para service de funcionário quando dados estiverem corretos")
    public void salvarFuncionario_deveSalvarUsuarioFuncionario_quandoDadosEstiveremCorretos() {
        when(permissaoService.buscarPorCodigo(any())).thenReturn(umaPermissaoFuncionario());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(umUsuario());

        var request = umUsuarioRequest();
        var response = service.salvarFuncionario(request, 1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Funcionário inserido com sucesso!");

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(funcionarioService, times(1)).salvarFuncionario(any(Usuario.class), anyInt());
    }

    @Test
    @DisplayName("Deve salvar usuário quando dados estiverem corretos")
    public void salvarUsuario_deveSalvarUsuario_quandoDadosEstiveremCorretos() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(umUsuario());

        var usuarioSalvo = service.salvarUsuario(umUsuario());

        assertThat(usuarioSalvo).isNotNull();

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando CPF estiver inválido")
    public void salvarUsuario_deveLancarException_quandoCpfForInvalido() {
        var usuario = umUsuario();
        usuario.setCpf("103.324.589-53");

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("O CPF está inválido.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando CPF estiver nulo")
    public void salvarUsuario_deveLancarException_quandoCpfForNulo() {
        var usuario = umUsuario();
        usuario.setCpf(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("O CPF deve ser informado.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando data de nascimento for igual à data de hoje")
    public void salvarUsuario_deveLancarException_quandoDataNascimentoForIgualHoje() {
        var usuario = umUsuario();
        usuario.setDataNascimento(LocalDate.now());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("A data de nascimento não pode ser igual à data de hoje.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando data de nascimento for superior à data de hoje")
    public void salvarUsuario_deveLancarException_quandoDataNascimentoForSuperiorHoje() {
        var usuario = umUsuario();
        usuario.setDataNascimento(LocalDate.now().plusDays(1));

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("A data de nascimento não pode ser superior à data de hoje.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando email já existir para usuário com ID diferente")
    public void salvarUsuario_deveLancarException_quandoEmailJaExistirSalvoParaOutroId() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(umUsuario()));

        var usuario = umUsuario();
        usuario.setId(123);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("Email já cadastrado para um usuário ativo.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando email já existir para usuário")
    public void salvarUsuario_deveLancarException_quandoEmailJaExistir() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(umUsuario()));

        var usuario = umUsuario();
        usuario.setId(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("Email já cadastrado para um usuário ativo.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando CPF já existir para usuário com ID diferente")
    public void salvarUsuario_deveLancarException_quandoCpfJaExistirSalvoParaOutroId() {
        when(usuarioRepository.findByCpf(anyString())).thenReturn(Optional.of(umUsuario()));

        var usuario = umUsuario();
        usuario.setId(123);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("CPF já cadastrado para um usuário ativo.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando CPF já existir para usuário")
    public void salvarUsuario_deveLancarException_quandoCpfJaExistir() {
        when(usuarioRepository.findByCpf(anyString())).thenReturn(Optional.of(umUsuario()));

        var usuario = umUsuario();
        usuario.setId(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarUsuario(usuario))
            .withMessage("CPF já cadastrado para um usuário ativo.");

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve editar dados do usuário quando dados estiverem corretos")
    public void editarDadosUsuario_deveEditarDados_quandoDadosCorretosComPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(umUsuario()));

        var request = umUsuarioRequest();
        var response = service.editarDadosUsuario(request, 1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Usuário alterado com sucesso!");

        verify(usuarioRepository, times(1)).findById(anyInt());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve editar dados do usuário quando usuário for admin")
    public void editarDadosUsuario_deveEditarDados_quandoDadosCorretosSendoAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(umUsuario()));

        var request = umUsuarioRequest();
        var response = service.editarDadosUsuario(request, 1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Usuário alterado com sucesso!");

        verify(usuarioRepository, times(1)).findById(anyInt());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário funcionário tentar editar dados e não tiver permissão")
    public void editarDadosUsuario_deveLancarException_quandoUsuarioNaoTiverPermissaoParaAlterarDados() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());

        var request = umUsuarioRequest();
        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.editarDadosUsuario(request, 10000))
            .withMessage("Você não possui permissão para alterar os dados desse usuário.");

        verify(usuarioRepository, times(0)).findById(anyInt());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }
}
