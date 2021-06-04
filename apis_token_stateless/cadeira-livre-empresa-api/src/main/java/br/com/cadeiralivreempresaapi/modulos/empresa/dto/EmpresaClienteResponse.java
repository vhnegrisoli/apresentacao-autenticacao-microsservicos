package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.horario.HorarioResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoResponse;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaClienteResponse {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String razaoSocial;
    private List<ProprietarioSocioClienteResponse> proprietarioSocios;
    private String tipoEmpresa;
    private List<ServicoResponse> servicos;
    private List<HorarioResponse> horarios;

    public static EmpresaClienteResponse of(Empresa empresa,
                                            List<ServicoResponse> servicos,
                                            List<HorarioResponse> horarios) {
        var response = new EmpresaClienteResponse();
        BeanUtils.copyProperties(empresa, response);
        response.setProprietarioSocios(empresa
            .getSocios()
            .stream()
            .map(ProprietarioSocioClienteResponse::of)
            .collect(Collectors.toList()));
        response.setTipoEmpresa(empresa.getTipoEmpresa().getTipoEmpresa());
        response.setServicos(isEmpty(servicos) ? Collections.emptyList() : servicos);
        response.setHorarios(isEmpty(horarios) ? Collections.emptyList() : horarios);
        return response;
    }
}
