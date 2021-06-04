package br.com.cadeiralivreempresaapi.modulos.usuario.model;

import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioAutenticado;
import br.com.cadeiralivreempresaapi.modulos.usuario.dto.UsuarioRequest;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESexo;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.*;
import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario.ATIVO;
import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NOME", nullable = false, length = 120)
    private String nome;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "ULTIMO_ACESSO")
    private LocalDateTime ultimoAcesso;

    @Column(name = "CPF", nullable = false, length = 14)
    private String cpf;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "SEXO", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ESexo sexo;

    @NotNull
    @JoinTable(name = "USUARIO_PERMISSAO", joinColumns = {
        @JoinColumn(name = "FK_USUARIO", foreignKey = @ForeignKey(name = "FK_USUARIO_PK"),
            referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PERMISSAO", foreignKey = @ForeignKey(name = "FK_PERMISSAO_PK"),
            referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permissao> permissoes;

    @Column(name = "TOKEN_NOTIFICACAO")
    private String tokenNotificacao;

    @Column(name = "SITUACAO", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESituacaoUsuario situacao;

    @PrePersist
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
        ultimoAcesso = dataCadastro;
        situacao = ATIVO;
    }

    public Integer getIdade() {
        return isEmpty(dataNascimento) ? 0 : Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean isAniversario() {
        var dataAtual = LocalDate.now();
        return !isEmpty(dataNascimento)
            && dataAtual.getDayOfMonth() == dataNascimento.getDayOfMonth()
            && dataAtual.getMonthValue() == dataNascimento.getMonthValue();
    }

    public boolean isAtivo() {
        return ATIVO.equals(situacao);
    }

    public boolean isProprietario() {
        return permissoes
            .stream()
            .map(Permissao::getPermissao)
            .anyMatch(permissao -> permissao.equals(PROPRIETARIO));
    }

    public boolean isSocio() {
        return permissoes
            .stream()
            .map(Permissao::getPermissao)
            .anyMatch(permissao -> permissao.equals(SOCIO));
    }

    public boolean isSocioOuProprietario() {
        return isSocio() || isProprietario();
    }

    public boolean isFuncionario() {
        return permissoes
            .stream()
            .map(Permissao::getPermissao)
            .anyMatch(permissao -> permissao.equals(FUNCIONARIO));
    }

    public boolean isNovoCadastro() {
        return isEmpty(id);
    }

    public boolean possuiToken(String novaToken) {
        return !isEmpty(tokenNotificacao) && tokenNotificacao.equals(novaToken);
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public static Usuario of(UsuarioRequest usuarioRequest) {
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioRequest, usuario);
        usuario.setDataCadastro(LocalDateTime.now());
        return usuario;
    }

    public static Usuario of(UsuarioAutenticado usuarioAutenticado) {
        return Usuario
            .builder()
            .id(usuarioAutenticado.getId())
            .build();
    }
}
