package br.com.cadeiralivreempresaapi.modulos.usuario.dto;

import br.com.cadeiralivreempresaapi.modulos.usuario.model.Permissao;
import br.com.cadeiralivreempresaapi.modulos.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private String nome;
    private String email;
    private String cpf;
    private Set<Permissao> permissoes;

    public static UsuarioResponse of(Usuario usuario) {
        return UsuarioResponse
            .builder()
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .cpf(usuario.getCpf())
            .permissoes(usuario.getPermissoes())
            .build();
    }
}
