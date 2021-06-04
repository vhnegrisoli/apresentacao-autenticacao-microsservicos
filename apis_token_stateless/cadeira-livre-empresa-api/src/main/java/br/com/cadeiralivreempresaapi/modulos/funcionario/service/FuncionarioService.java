package br.com.cadeiralivreempresaapi.modulos.funcionario.service;

import br.com.cadeiralivreempresaapi.modulos.comum.dto.PageRequest;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioFiltros;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioPageResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.dto.FuncionarioResponse;
import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;
import br.com.cadeiralivreempresaapi.modulos.funcionario.repository.FuncionarioRepository;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static br.com.cadeiralivreempresaapi.modulos.funcionario.messages.FuncionarioMessages.FUNCIONARIO_NAO_ENCONTRADO;
import static br.com.cadeiralivreempresaapi.modulos.funcionario.messages.FuncionarioMessages.FUNCIONARIO_USUARIO_SEM_PERMISSAO;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private AutenticacaoService autenticacaoService;

    public void salvarFuncionario(Usuario usuario, Integer empresaId) {
        var funcionario = Funcionario.of(usuario, new Empresa(empresaId));
        validarPermissoesDoUsuario(funcionario);
        funcionarioRepository.save(funcionario);
    }

    public Page<FuncionarioPageResponse> buscarTodos(PageRequest pageable, FuncionarioFiltros filtros) {
        validarFiltrosUsuario(filtros);
        return funcionarioRepository.findAll(filtros.toPredicate().build(), pageable)
            .map(FuncionarioPageResponse::of);
    }

    public FuncionarioResponse buscarPorId(Integer id) {
        var funcionario = funcionarioRepository.findById(id)
            .orElseThrow(() -> FUNCIONARIO_NAO_ENCONTRADO);
        validarPermissoesDoUsuario(funcionario);
        return FuncionarioResponse.of(funcionario);
    }

    public void validarUsuario(Integer usuarioId) {
        var funcionario = funcionarioRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> FUNCIONARIO_NAO_ENCONTRADO);
        validarPermissoesDoUsuario(funcionario);
    }

    private void validarFiltrosUsuario(FuncionarioFiltros filtros) {
        var usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();
        if (!usuarioAutenticado.isAdmin()) {
            if (usuarioAutenticado.isFuncionario()) {
                filtros.setUsuarioId(usuarioAutenticado.getId());
            }
            if (usuarioAutenticado.isSocioOuProprietario()) {
                filtros.setSocioId(usuarioAutenticado.getId());
            }
        }
    }

    private void validarPermissoesDoUsuario(Funcionario funcionario) {
        var usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();
        if (isFuncionarioSemPermissao(usuarioAutenticado, funcionario)
            || isSocioProprietarioSemPermissao(usuarioAutenticado, funcionario)) {
            throw FUNCIONARIO_USUARIO_SEM_PERMISSAO;
        }
    }

    private boolean isFuncionarioSemPermissao(UsuarioAutenticado usuarioAutenticado, Funcionario funcionario) {
        return usuarioAutenticado.isFuncionario()
            && !usuarioAutenticado.getId().equals(funcionario.getUsuario().getId());
    }

    private boolean isSocioProprietarioSemPermissao(UsuarioAutenticado usuarioAutenticado, Funcionario funcionario) {
        return usuarioAutenticado.isSocioOuProprietario()
            && !empresaService.existeEmpresaParaUsuario(funcionario.getEmpresa().getId(), usuarioAutenticado.getId());
    }

    public boolean existeUsuarioParaEmpresa(Integer empresaId, Integer usuarioId) {
        return funcionarioRepository.existsByUsuarioIdAndEmpresaId(usuarioId, empresaId);
    }
}
