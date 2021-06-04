package br.com.cadeiralivreempresaapi.modulos.agenda.mocks;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.agenda.ClienteRequest;

public class ClienteMocks {

    public static ClienteRequest umClienteRequest() {
        return ClienteRequest
            .builder()
            .id("asdasdas15151515")
            .cpf("460.427.120-80")
            .email("cliente@gmail.com")
            .nome("Cliente")
            .build();
    }
}
