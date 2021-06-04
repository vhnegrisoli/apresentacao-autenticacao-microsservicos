package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioAcessoServiceTest {

    @InjectMocks
    private UsuarioAcessoService service;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private FuncionarioService funcionarioService;
    @Mock
    private EmpresaService empresaService;

    @Test
    @DisplayName("Deve fazer nada quando usuário for admin")
    public void validarPermissoesDoUsuario_deveFazerNada_quandoUsuarioForAdmin() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoAdmin());

        service.validarPermissoesDoUsuario(1);

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(0)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve validar usuário sócio quando usuário for sócio")
    public void validarPermissoesDoUsuario_deveValidarUsuarioSocio_quandoForSocio() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(true);

        service.validarPermissoesDoUsuario(1);

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(0)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve validar usuário proprietario quando usuário for proprietario")
    public void validarPermissoesDoUsuario_deveValidarUsuarioSocio_quandoForProprietario() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoProprietario());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(true);

        service.validarPermissoesDoUsuario(1);

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(0)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário sócio não possuir permissão")
    public void validarPermissoesDoUsuario_deveLancarException_quandoSocioNaoPossuirPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.validarPermissoesDoUsuario(1))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(0)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário proprietário não possuir permissão")
    public void validarPermissoesDoUsuario_deveLancarException_quandoProprietarioNaoPossuirPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoProprietario());
        when(empresaService.existeEmpresaParaUsuario(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.validarPermissoesDoUsuario(1))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");

        verify(empresaService, times(1)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(0)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve validar usuário funcionário quando usuário for funcionário")
    public void validarPermissoesDoUsuario_deveValidarUsuarioFuncionario_quandoForFuncionario() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());
        when(funcionarioService.existeUsuarioParaEmpresa(anyInt(), anyInt())).thenReturn(true);

        service.validarPermissoesDoUsuario(1);

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(1)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando usuário funcionário não possuir permissão")
    public void validarPermissoesDoUsuario_deveLancarException_quandoFuncionarioNaoPossuirPermissao() {
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoFuncionario());
        when(funcionarioService.existeUsuarioParaEmpresa(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.validarPermissoesDoUsuario(1))
            .withMessage("Usuário sem permissão para visualizar funcionários desta empresa.");

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(funcionarioService, times(1)).existeUsuarioParaEmpresa(anyInt(), anyInt());
    }
}
