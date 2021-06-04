package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.enums.ESituacaoUsuario;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.PROPRIETARIO;
import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.SOCIO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioSocioResponse {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String descricao;
    private ESituacaoUsuario situacao;

    public static ProprietarioSocioResponse of(Usuario usuario) {
        var response = new ProprietarioSocioResponse();
        BeanUtils.copyProperties(usuario, response);
        response.setDescricao(usuario.isProprietario()
            ? PROPRIETARIO.getDescricao()
            : SOCIO.getDescricao());
        return response;
    }
}
