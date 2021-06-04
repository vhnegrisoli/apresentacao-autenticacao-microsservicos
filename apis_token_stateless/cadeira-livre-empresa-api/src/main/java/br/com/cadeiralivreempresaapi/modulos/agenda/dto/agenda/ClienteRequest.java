package br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String id;
    private String nome;
    private String email;
    private String cpf;
}
