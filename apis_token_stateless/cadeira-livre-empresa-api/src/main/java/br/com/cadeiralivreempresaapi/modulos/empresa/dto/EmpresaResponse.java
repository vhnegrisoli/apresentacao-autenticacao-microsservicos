package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cadeiralivreempresaapi.modulos.comum.util.PatternUtil.DATE_TIME_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String razaoSocial;
    private ESituacaoEmpresa situacao;
    private List<ProprietarioSocioResponse> proprietarioSocios;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime dataCadastro;
    private ETipoEmpresa tipoEmpresa;
    private Integer tempoRefreshCadeiraLivre;
    private List<EnderecoResponse> enderecos;

    public static EmpresaResponse of(Empresa empresa,
                                     List<Endereco> enderecos) {
        var response = new EmpresaResponse();
        BeanUtils.copyProperties(empresa, response);
        response.setProprietarioSocios(empresa
            .getSocios()
            .stream()
            .map(ProprietarioSocioResponse::of)
            .collect(Collectors.toList()));
        response.setEnderecos(enderecos
            .stream()
            .map(EnderecoResponse::converterDe)
            .collect(Collectors.toList()));
        return response;
    }
}
