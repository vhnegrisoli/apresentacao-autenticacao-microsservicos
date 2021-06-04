package br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda;

import br.com.cadeiralivreempresaapi.modulos.agenda.model.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private String id;
    private String nome;
    private String email;
    private String cpf;

    public static ClienteResponse of(Agenda agenda) {
        return ClienteResponse
            .builder()
            .id(agenda.getClienteId())
            .nome(agenda.getClienteNome())
            .email(agenda.getClienteEmail())
            .cpf(agenda.getClienteCpf())
            .build();
    }
}
