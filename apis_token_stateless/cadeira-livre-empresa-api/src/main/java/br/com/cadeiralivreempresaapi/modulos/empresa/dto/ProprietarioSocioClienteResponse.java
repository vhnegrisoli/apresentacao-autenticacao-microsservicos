package br.com.cadeiralivreempresaapi.modulos.empresa.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.PROPRIETARIO;
import static br.com.cadeiralivreempresaapi.modulos.usuario.enums.EPermissao.SOCIO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioSocioClienteResponse {

    private Integer id;
    private String nome;
    private String descricao;

    public static ProprietarioSocioClienteResponse of(Usuario usuario) {
        return ProprietarioSocioClienteResponse
            .builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .descricao(usuario.isProprietario()
                ? PROPRIETARIO.getDescricao()
                : SOCIO.getDescricao()
            )
            .build();
    }
}
