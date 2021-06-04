package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cadeiralivreempresaapi.modulos.empresa.messages.EmpresaMessages.EMPRESA_USUARIO_SEM_PERMISSAO;
import static br.com.cadeiralivreempresaapi.modulos.funcionario.messages.FuncionarioMessages.FUNCIONARIO_USUARIO_SEM_PERMISSAO;

@Service
public class UsuarioAcessoService {

    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private EmpresaService empresaService;

    public void validarPermissoesDoUsuario(Integer empresaId) {
        var usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();
        if (usuarioAutenticado.isSocioOuProprietario()
            && !empresaService.existeEmpresaParaUsuario(empresaId, usuarioAutenticado.getId())) {
            throw EMPRESA_USUARIO_SEM_PERMISSAO;
        }
        if (usuarioAutenticado.isFuncionario()
            && !funcionarioService.existeUsuarioParaEmpresa(empresaId, usuarioAutenticado.getId())) {
            throw FUNCIONARIO_USUARIO_SEM_PERMISSAO;
        }
    }
}
