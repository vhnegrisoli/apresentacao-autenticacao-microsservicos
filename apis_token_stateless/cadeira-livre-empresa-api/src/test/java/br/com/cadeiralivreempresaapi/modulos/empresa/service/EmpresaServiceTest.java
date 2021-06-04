package br.com.cadeiralivreempresaapi.modulos.empresa.service;

import br.com.cadeiralivreempresaapi.config.exception.PermissaoException;
import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.repository.EmpresaRepository;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;
import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresaRequest;
import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @InjectMocks
    private EmpresaService service;
    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private EnderecoService enderecoService;

    @Test
    @DisplayName("Deve salvar empresa quando dados estiverem corretos")
    public void salvar_deveSalvarNovaEmpresa_quandoDadosEstiveremCorretos() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        when(autenticacaoService.getUsuarioAutenticadoId()).thenReturn(1);
        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuario);
        when(empresaRepository.save(any())).thenReturn(umaEmpresa());

        var response = service.salvar(umaEmpresaRequest());

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A empresa foi criada com sucesso!");

        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar empresa quando usuário não informar o nome da empresa")
    public void salvar_deveLancarException_quandoUsuarioNaoInformarNomeDaEmpresa() {
        var empresaSemNome = umaEmpresaRequest();
        empresaSemNome.setNome(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvar(empresaSemNome))
            .withMessage("É obrigatório informar o nome da empresa.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar empresa quando usuário não informar o CPF/CNPJ da empresa")
    public void salvar_deveLancarException_quandoUsuarioNaoInformarCpfCnpjDaEmpresa() {
        var empresaSemCpfCnpj = umaEmpresaRequest();
        empresaSemCpfCnpj.setCpfCnpj(null);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvar(empresaSemCpfCnpj))
            .withMessage("É obrigatório informar o CPF ou CNPJ da empresa.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar empresa quando usuário não for sócio e nem proprietário")
    public void salvar_deveLancarException_quandoUsuarioNaoForSocioNemProprietario() {
        when(autenticacaoService.getUsuarioAutenticadoId()).thenReturn(1);
        when(usuarioService.buscarPorId(anyInt())).thenReturn(umUsuario());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvar(umaEmpresaRequest()))
            .withMessage("Para salvar uma empresa, o usuário deve ser um proprietário.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar empresa quando não informar ao menos um endereço")
    public void salvar_deveLancarException_quandoNaoInformarEndereco() {
        var requestSemEndereco = umaEmpresaRequest();
        requestSemEndereco.setEnderecos(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvar(requestSemEndereco))
            .withMessage("É necessário informar o endereço da empresa.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve editar com quando dados estiverem corretos e usuário possuir permissão")
    public void editar_deveEditarEmpresa_quandoDadosEstiveremCorretosEUsuarioPossuirPermissao() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(true);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(umaEmpresa()));

        var response = service.editar(umaEmpresaRequest(), 1);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("A empresa foi alterada com sucesso!");

        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar editar empresa não existente")
    public void editar_deveLancarException_quandoEmpresaNaoExistir() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(true);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.editar(umaEmpresaRequest(), 1))
            .withMessage("A empresa não foi encontrada.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentar editar empresa inativa")
    public void editar_deveLancarException_quandoEmpresaEstiverInativa() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        var empresa = umaEmpresa();
        empresa.setSituacao(ESituacaoEmpresa.INATIVA);
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(true);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.editar(umaEmpresaRequest(), 1))
            .withMessage("Não é possível cadastrar um funcionário para uma empresa inativa.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve inserir sócio quando dados estiverem corretos e usuário for proprietário")
    public void inserirSocio_deveInserirSocio_quandoDadosEstiveremCorretosEUsuarioForProprietario() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoProprietario()));
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(umaEmpresa()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoProprietario());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(true);

        service.inserirSocio(usuario, 1);

        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve inserir sócio quando dados estiverem corretos e usuário for sócio")
    public void inserirSocio_deveInserirSocio_quandoDadosEstiveremCorretosEUsuarioForSocio() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(umaEmpresa()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(true);

        service.inserirSocio(usuario, 1);

        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Deve lançar exception quando tentarinserir sócio e usuário admin e não possuir permissão")
    public void inserirSocio_deveLancarException_quandoUsuarioNaoPossuirPermissaoENaoForAdmin() {
        var usuario = umUsuario();
        usuario.setPermissoes(Set.of(umaPermissaoSocio()));
        when(autenticacaoService.getUsuarioAutenticado()).thenReturn(umUsuarioAutenticadoSocio());
        when(empresaRepository.existsByIdAndSociosId(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(PermissaoException.class)
            .isThrownBy(() -> service.editar(umaEmpresaRequest(), 1))
            .withMessage("Usuário sem permissão para visualizar essa empresa.");

        verify(empresaRepository, times(0)).save(any(Empresa.class));
    }
}
