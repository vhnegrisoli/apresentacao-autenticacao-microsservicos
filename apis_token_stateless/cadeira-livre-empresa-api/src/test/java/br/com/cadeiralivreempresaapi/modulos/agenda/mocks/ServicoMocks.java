package br.com.cadeiralivreempresaapi.modulos.agenda.mocks;

import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoRequest;
import br.com.cadeiralivreempresaapi.modulos.agenda.dto.servico.ServicoResponse;
import br.com.cadeiralivreempresaapi.modulos.agenda.model.Servico;

import static br.com.cadeiralivreempresaapi.modulos.empresa.mocks.EmpresaMocks.umaEmpresa;

public class ServicoMocks {

    public static Servico umServico() {
        return Servico
            .builder()
            .id(1)
            .descricao("Corte de cabelo")
            .empresa(umaEmpresa())
            .preco(25.00)
            .build();
    }

    public static ServicoRequest umServicoRequest() {
        return ServicoRequest
            .builder()
            .descricao("Corte de cabelo")
            .empresaId(1)
            .preco(25.00)
            .build();
    }

    public static ServicoResponse umServicoResponse() {
        var empresa = umaEmpresa();
        return ServicoResponse
            .builder()
            .id(1)
            .descricao("Corte de cabelo")
            .empresa(empresa.getNome())
            .cpfCnpj(empresa.getCpfCnpj())
            .preco(25.00)
            .build();
    }
}
