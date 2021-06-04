package br.com.cadeiralivreempresaapi.modulos.usuario.service;

import br.com.cadeiralivreempresaapi.modulos.comum.response.SuccessResponseDetails;
import br.com.cadeiralivreempresaapi.modulos.empresa.service.EmpresaService;
import br.com.cadeiralivreempresaapi.modulos.funcionario.service.FuncionarioService;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioRequest;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.repository.UsuarioRepository;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.empresa.messages.EmpresaMessages.PROPRIETARIO_CRIADO_SUCESSO;
import static br.com.cadeiralivreempresaapi.modulos.empresa.messages.EmpresaMessages.SOCIO_CRIADO_SUCESSO;
import static br.com.cadeiralivreempresaapi.modulos.funcionario.messages.FuncionarioMessages.FUNCIONARIO_CRIADO_SUCESSO;
import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.*;
import static br.com.cadeiralivreempresaapi.modulos.usuario.messages.UsuarioMessages.*;
import static br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario.of;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@SuppressWarnings("PMD.TooManyStaticImports")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private PermissaoService permissaoService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private FuncionarioService funcionarioService;

    @Transactional
    public SuccessResponseDetails salvarProprietario(UsuarioRequest usuarioRequest) {
        var usuario = of(usuarioRequest);
        usuario.setPermissoes(Set.of(permissaoService.buscarPorCodigo(PROPRIETARIO)));
        salvarUsuario(usuario);
        return PROPRIETARIO_CRIADO_SUCESSO;
    }

    @Transactional
    public SuccessResponseDetails salvarSocio(UsuarioRequest usuarioRequest, Integer empresaId) {
        var usuario = of(usuarioRequest);
        usuario.setPermissoes(Set.of(permissaoService.buscarPorCodigo(SOCIO)));
        empresaService.inserirSocio(salvarUsuario(usuario), empresaId);
        return SOCIO_CRIADO_SUCESSO;
    }

    @Transactional
    public SuccessResponseDetails salvarFuncionario(UsuarioRequest usuarioRequest, Integer empresaId) {
        var usuario = of(usuarioRequest);
        usuario.setPermissoes(Set.of(permissaoService.buscarPorCodigo(FUNCIONARIO)));
        funcionarioService.salvarFuncionario(salvarUsuario(usuario), empresaId);
        return FUNCIONARIO_CRIADO_SUCESSO;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        validarDadosUsuario(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public SuccessResponseDetails editarDadosUsuario(UsuarioRequest usuarioRequest, Integer id) {
        validarPermissoesUsuario(id);
        usuarioRequest.setId(id);
        var usuario = of(usuarioRequest);
        validarDadosUsuario(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        var usuarioExistente = buscarPorId(usuarioRequest.getId());
        usuario.setPermissoes(usuarioExistente.getPermissoes());
        usuario.setSituacao(usuarioExistente.getSituacao());
        usuarioRepository.save(usuario);
        return USUARIO_ALTERADO_SUCESSO;
    }

    private void validarDadosUsuario(Usuario usuario) {
        validarCpf(usuario);
        validarDataNascimento(usuario);
        validarEmailExistente(usuario);
        validarCpfExistente(usuario);
    }

    private void validarCpf(Usuario usuario) {
        if (!isEmpty(usuario.getCpf())) {
            try {
                var cpfValidator = new CPFValidator();
                cpfValidator.assertValid(usuario.getCpf());
            } catch (Exception ex) {
                throw CPF_INVALIDO;
            }
        } else {
            throw CPF_NAO_INFORMADO;
        }
    }

    private void validarDataNascimento(Usuario usuario) {
        if (usuario.getDataNascimento().isEqual(LocalDate.now())) {
            throw DATA_NASCIMENTO_IGUAL_HOJE;
        }
        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw DATA_NASCIMENTO_MAIOR_HOJE;
        }
    }

    private void validarEmailExistente(Usuario usuario) {
        usuarioRepository.findByEmail(usuario.getEmail())
            .ifPresent(usuarioExistente -> {
                if (usuario.isNovoCadastro() || !usuario.getId().equals(usuarioExistente.getId())) {
                    throw EMAIL_JA_CADASTRADO;
                }
            });
    }

    private void validarCpfExistente(Usuario usuario) {
        usuarioRepository.findByCpf(usuario.getCpf())
            .ifPresent(usuarioExistente -> {
                if (usuario.isNovoCadastro() || !usuario.getId().equals(usuarioExistente.getId())) {
                    throw CPF_JA_CADASTRADO;
                }
            });
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> USUARIO_NAO_ENCONTRADO);
    }

    @Transactional
    public UsuarioAutenticado getUsuarioAutenticadoAtualizaUltimaData() {
        var usuarioAtualizado = usuarioRepository
            .findById(autenticacaoService.getUsuarioAutenticadoId())
            .orElseThrow(() -> USUARIO_NAO_ENCONTRADO);
        return UsuarioAutenticado.of(atualizarUltimoAcesso(usuarioAtualizado));
    }

    @Transactional
    private Usuario atualizarUltimoAcesso(Usuario usuario) {
        usuario.setUltimoAcesso(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public SuccessResponseDetails atualizarTokenNotificacao(String token) {
        var usuario = usuarioRepository
            .findById(autenticacaoService.getUsuarioAutenticadoId())
            .orElseThrow(() -> USUARIO_NAO_ENCONTRADO);
        if (!usuario.possuiToken(token)) {
            usuario.setTokenNotificacao(token);
            usuarioRepository.save(usuario);
            return TOKEN_ATUALIZADO;
        }
        return TOKEN_EXISTENTE;
    }

    private void validarPermissoesUsuario(Integer usuarioId) {
        var usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();
        if (!usuarioAutenticado.isAdmin() && !usuarioAutenticado.getId().equals(usuarioId)) {
            throw SEM_PERMISSAO_EDITAR;
        }
    }

    @Transactional
    public SuccessResponseDetails alterarSituacao(Integer id) {
        var usuario = buscarPorId(id);
        validarUsuarioFuncionario(usuario);
        validarUsuarioSocio(usuario);
        usuario.setSituacao(usuario.isAtivo()
            ? ESituacaoUsuario.INATIVO
            : ESituacaoUsuario.ATIVO);
        usuarioRepository.save(usuario);
        return USUARIO_ALTERACAO_SITUACAO_SUCESSO;
    }

    private void validarUsuarioFuncionario(Usuario usuario) {
        if (usuario.isFuncionario()) {
            funcionarioService.validarUsuario(usuario.getId());
        }
    }

    private void validarUsuarioSocio(Usuario usuario) {
        var usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();
        if (usuario.isSocioOuProprietario() && !usuarioAutenticado.isAdmin()) {
            throw SEM_PERMISSAO_ALTERAR_SITUACAO;
        }
    }
}
