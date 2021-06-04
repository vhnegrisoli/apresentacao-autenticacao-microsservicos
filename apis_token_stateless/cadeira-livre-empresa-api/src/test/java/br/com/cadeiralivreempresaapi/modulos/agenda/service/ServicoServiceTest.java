package br.com.cadeiralivreempresaapi.modulos.agenda.service;

import br.com.cadeiralivreempresaapi.config.exception.ValidacaoException;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Servico;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.AgendaRepository;
import br.com.cadeiralivreempresaapi.modulos.agenda.repository.ServicoRepository;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.UsuarioAcessoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServico;
import static br.com.cadeiralivreempresaapi.modulos.agenda.mocks.ServicoMocks.umServicoRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicoServiceTest {

    @InjectMocks
    private ServicoService service;
    @Mock
    private ServicoRepository repository;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private UsuarioAcessoService acessoService;

    @Test
    @DisplayName("Deve salvar novo serviço quando dados estiverem corretos")
    public void salvarNovoServico_deveSalvarNovoServico_quandoDadosEstiveremCorretos() {
        when(repository.save(any())).thenReturn(umServico());
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.salvarNovoServico(umServicoRequest());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte de cabelo");
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01");
        assertThat(response.getPreco()).isEqualTo(25.00);

        verify(repository, times(1)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(repository, times(1)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando request estiver vazio")
    public void salvarNovoServico_deveLancarException_quandoRequestEstiverVazio() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(null))
            .withMessage("É obrigatório enviar os dados do serviço.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando descrição estiver vazia")
    public void salvarNovoServico_deveLancarException_quandoDescricaoEstiverVazia() {
        var request = umServicoRequest();
        request.setDescricao(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(request))
            .withMessage("É obrigatório informar a descrição.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando empresa estiver vazia")
    public void salvarNovoServico_deveLancarException_quandoEmpresaEstiverVazia() {
        var request = umServicoRequest();
        request.setEmpresaId(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(request))
            .withMessage("É obrigatório informar a empresa.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando preço estiver vazio")
    public void salvarNovoServico_deveLancarException_quandoPrecoEstiverVazio() {
        var request = umServicoRequest();
        request.setPreco(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(request))
            .withMessage("É obrigatório informar o preço.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando preço for menor que zero")
    public void salvarNovoServico_deveLancarException_quandoPrecoMenorQueZero() {
        var request = umServicoRequest();
        request.setPreco(-1.0);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(request))
            .withMessage("O preço não pode ser menor ou igual a R$0,00.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço quando preço for igual a zero")
    public void salvarNovoServico_deveLancarException_quandoPrecoIgualAZero() {
        var request = umServicoRequest();
        request.setPreco(-1.0);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(request))
            .withMessage("O preço não pode ser menor ou igual a R$0,00.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar salvar serviço com descrição existente para a mesma empresa")
    public void salvarNovoServico_deveLancarException_quandoJaExistirDescricaoParaMesmaEmpresa() {
        when(repository.existsByDescricaoAndEmpresaId(anyString(), anyInt())).thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.salvarNovoServico(umServicoRequest()))
            .withMessage("Este serviço já existe para esta empresa.");

        verify(repository, times(1)).existsByDescricaoAndEmpresaId(anyString(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve editar serviço quando dados estiverem corretos")
    public void atualizarServico_deveAtualizarServico_quandoDadosEstiveremCorretos() {
        when(repository.save(any())).thenReturn(umServico());
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.atualizarServico(umServicoRequest(), 1);

        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getDescricao()).isEqualTo("Corte de cabelo");
        assertThat(response.getCpfCnpj()).isEqualTo("82.765.926/0001-32");
        assertThat(response.getEmpresa()).isEqualTo("Empresa 01");
        assertThat(response.getPreco()).isEqualTo(25.00);

        verify(repository, times(1)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(1)).buscarPorId(anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(repository, times(1)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando request estiver vazio")
    public void atualizarServico_deveLancarException_quandoRequestEstiverVazio() {
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(null, 1))
            .withMessage("É obrigatório enviar os dados do serviço.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando descrição estiver vazia")
    public void atualizarServico_deveLancarException_quandoDescricaoEstiverVazia() {
        var request = umServicoRequest();
        request.setDescricao(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(request, 1))
            .withMessage("É obrigatório informar a descrição.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando empresa estiver vazia")
    public void atualizarServico_deveLancarException_quandoEmpresaEstiverVazia() {
        var request = umServicoRequest();
        request.setEmpresaId(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(request, 1))
            .withMessage("É obrigatório informar a empresa.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando preço estiver vazio")
    public void atualizarServico_deveLancarException_quandoPrecoEstiverVazio() {
        var request = umServicoRequest();
        request.setPreco(null);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(request, 1))
            .withMessage("É obrigatório informar o preço.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando preço for menor que zero")
    public void atualizarServico_deveLancarException_quandoPrecoMenorQueZero() {
        var request = umServicoRequest();
        request.setPreco(-1.0);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(request, 1))
            .withMessage("O preço não pode ser menor ou igual a R$0,00.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço quando preço for igual a zero")
    public void atualizarServico_deveLancarException_quandoPrecoIgualAZero() {
        var request = umServicoRequest();
        request.setPreco(-1.0);
        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(request, 1))
            .withMessage("O preço não pode ser menor ou igual a R$0,00.");

        verify(repository, times(0)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar editar serviço com descrição existente para a mesma empresa")
    public void atualizarServico_deveLancarException_quandoJaExistirDescricaoParaMesmaEmpresa() {
        when(repository.existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt())).thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.atualizarServico(umServicoRequest(), 1))
            .withMessage("Este serviço já existe para esta empresa.");

        verify(repository, times(1)).existsByDescricaoAndEmpresaIdAndIdNot(anyString(), anyInt(), anyInt());
        verify(empresaService, times(0)).buscarPorId(anyInt());
        verify(repository, times(0)).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve não fazer nada caso existam todos os serviços")
    public void validarServicosExistentes_deveNaoFazerNada_quandoExistiremTodosOsServicos() {
        when(repository.existsByIdAndEmpresaId(anyInt(), anyInt())).thenReturn(true);

        service.validarServicosExistentesPorEmpresa(List.of(
            umServico(),
            umServico(),
            umServico(),
            umServico()
        ), 1);

        verify(repository, times(4)).existsByIdAndEmpresaId(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve lançar exception quando algum serviço não existir por ID")
    public void validarServicosExistentes_deveLancarException_quandoAlgumServicoNaoExistirPorId() {
        when(repository.existsByIdAndEmpresaId(anyInt(), anyInt())).thenReturn(false);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.validarServicosExistentesPorEmpresa(List.of(
                umServico(),
                umServico(),
                umServico(),
                umServico()
            ), 2))
            .withMessage("O serviço não foi encontrado.");

        verify(repository, times(1)).existsByIdAndEmpresaId(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve retornar serviços separados por vírgula")
    public void tratarNomesServicos_deveRetornarServicosSeparadaosPorVirgula_quandoSolicitadaListaDeServicos() {
        var servico1 = umServico();
        var servico2 = umServico();
        servico2.setDescricao("Lavar cabelo");
        var servico3 = umServico();
        servico3.setDescricao("Lavar e cortar");
        assertThat(service.tratarNomesServicos(List.of(servico1, servico2, servico3)))
            .isEqualTo("Corte de cabelo, Lavar cabelo, Lavar e cortar");
    }

    @Test
    @DisplayName("Deve remover serviço por ID quando usuário for admin")
    public void removerServicoPorId_deveRemoverServicoPorId_quandoUsuarioForAdmin() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.removerServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Serviço removido com sucesso!");

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(agendaRepository, times(1)).existsByServicosId(anyInt());
        verify(repository, times(1)).delete(any(Servico.class));
    }

    @Test
    @DisplayName("Deve remover serviço por ID quando usuário for proprietário")
    public void removerServicoPorId_deveRemoverServicoPorId_quandoUsuarioForProprietario() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.removerServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Serviço removido com sucesso!");

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(agendaRepository, times(1)).existsByServicosId(anyInt());
        verify(repository, times(1)).delete(any(Servico.class));
    }

    @Test
    @DisplayName("Deve remover serviço por ID quando usuário for sócio")
    public void removerServicoPorId_deveRemoverServicoPorId_quandoUsuarioForSocio() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.removerServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Serviço removido com sucesso!");

        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(agendaRepository, times(1)).existsByServicosId(anyInt());
        verify(repository, times(1)).delete(any(Servico.class));
    }

    @Test
    @DisplayName("Deve remover serviço por ID quando usuário for funcionário")
    public void removerServicoPorId_deveRemoverServicoPorId_quandoUsuarioForFuncionario() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));

        var response = service.removerServicoPorId(1);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Serviço removido com sucesso!");

        verify(empresaService, times(0)).existeEmpresaParaUsuario(anyInt(), anyInt());
        verify(agendaRepository, times(1)).existsByServicosId(anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(repository, times(1)).delete(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exception ao tentar remover serviço por ID quando já estiver presente em agenda")
    public void removerServicoPorId_deveLancarException_quandoServicoEstiverPresenteEmAgenda() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(umServico()));
        when(agendaRepository.existsByServicosId(anyInt())).thenReturn(true);

        assertThatExceptionOfType(ValidacaoException.class)
            .isThrownBy(() -> service.removerServicoPorId(1))
            .withMessage("O serviço não pode ser removido pois já está cadastrado para uma agenda.");

        verify(agendaRepository, times(1)).existsByServicosId(anyInt());
        verify(acessoService, times(1)).validarPermissoesDoUsuario(anyInt());
        verify(repository, times(0)).delete(any(Servico.class));
    }
}