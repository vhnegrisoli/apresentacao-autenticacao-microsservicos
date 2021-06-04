package br.com.cadeiralivreempresaapi.modulos.funcionario.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;
import br.com.cadeiralivreempresaapi.modulos.funcionario.repository.FuncionarioRepository;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.cadeiralivreempresaapi.modulos.funcionario.mocks.FuncionarioMocks.umFuncionario;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService service;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private AutenticacaoService autenticacaoService;

    @Test
    @DisplayName("Deve salvar um funcionário quando dados estiverem corretos e usuário sendo admin")
    public void salvarFuncionario_deveSalvarFuncionario_quandoDadosCorretosEUsuarioForAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        service.salvarFuncionario(umUsuario(), 1);

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve salvar um funcionário quando dados estiverem corretos e usuário sendo proprietário")
    public void salvarFuncionario_deveSalvarFuncionario_quandoDadosCorretosEUsuarioForProprietario() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoProprietario());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(true);

        service.salvarFuncionario(umUsuario(), 1);

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve salvar um funcionário quando dados estiverem corretos e usuário sendo sócio")
    public void salvarFuncionario_deveSalvarFuncionario_quandoDadosCorretosEUsuarioForSocio() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(true);

        service.salvarFuncionario(umUsuario(), 1);

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve salvar um funcionário quando dados estiverem corretos e usuário sendo funcionário")
    public void salvarFuncionario_deveSalvarFuncionario_quandoDadosCorretosEUsuarioForFuncionario() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());

        service.salvarFuncionario(umUsuario(), 1);

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar salvar funcionário e proprietário não tiver permissão")
    public void salvarFuncionario_deveLancarException_quandoProprietarioNaoTiverPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoProprietario());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.salvarFuncionario(umUsuario(), 1))
            .withMessage("Usuário sem permissão para visualizar funcionários desta empresa.");

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(0)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar salvar funcionário e sócio não tiver permissão")
    public void salvarFuncionario_deveLancarException_quandoSocioNaoTiverPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.salvarFuncionario(umUsuario(), 1))
            .withMessage("Usuário sem permissão para visualizar funcionários desta empresa.");

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(0)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar salvar funcionário e funcionário não tiver permissão")
    public void salvarFuncionario_deveLancarException_quandoFuncionarioNaoTiverPermissao() {
        var funcionario = umUsuarioAutenticadoFuncionario();
        funcionario.setId(10);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(funcionario);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.salvarFuncionario(umUsuario(), 1))
            .withMessage("Usuário sem permissão para visualizar funcionários desta empresa.");

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), any());
        verify(funcionarioRepository, times(0)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve validar o usuário do funcionário quando solicitado por ID do usuário")
    public void validarUsuario_deveValidarUsuarioDoFuncionario_quandoInformarUsuarioId() {
        when(funcionarioRepository.findByUsuarioId(anyInt())).thenReturn(Optional.of(umFuncionario()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());

        service.validarUsuario(1);

        verify(funcionarioRepository, times(1)).findByUsuarioId(anyInt());
        verify(autenticacaoService, times(1)).getUsuarioAutenticado();
        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar validar funcionário e não encontrar por usuário ID")
    public void validarUsuario_deveLancarException_quandoNaoEncontrarFuncionarioPorUsuarioId() {
        when(funcionarioRepository.findByUsuarioId(anyInt())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.validarUsuario(1))
            .withMessage("O funcionário não foi encontrado.");

        verify(funcionarioRepository, times(1)).findByUsuarioId(anyInt());
        verify(autenticacaoService, times(0)).getUsuarioAutenticado();
        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
    }
}
