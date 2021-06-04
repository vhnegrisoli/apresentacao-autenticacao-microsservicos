package br.com.cadeiralivreempresaapi.modulos.funcionario.dto;

import br.com.cadeiralivreempresaapi.modulos.funcionario.model.Funcionario;
import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.PatternUtil.DATE_TIME_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioResponse {

    private Integer id;
    private Integer usuarioId;
    private String nome;
    private String email;
    private String cpf;
    private ESituacaoUsuario situacao;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime ultimoAcesso;
    private String empresa;
    private String cpfCnpj;

    public static FuncionarioResponse of(Funcionario funcionario) {
        return FuncionarioResponse
            .builder()
            .id(funcionario.getId())
            .usuarioId(funcionario.getUsuario().getId())
            .nome(funcionario.getUsuario().getNome())
            .email(funcionario.getUsuario().getEmail())
            .cpf(funcionario.getUsuario().getCpf())
            .situacao(funcionario.getUsuario().getSituacao())
            .ultimoAcesso(funcionario.getUsuario().getUltimoAcesso())
            .empresa(funcionario.getEmpresa().getNome())
            .cpfCnpj(funcionario.getEmpresa().getCpfCnpj())
            .build();
    }
}
