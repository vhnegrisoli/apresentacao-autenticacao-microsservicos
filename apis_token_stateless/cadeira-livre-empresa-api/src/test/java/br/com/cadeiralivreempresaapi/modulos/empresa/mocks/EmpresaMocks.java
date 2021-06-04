package br.com.cadeiralivreempresaapi.modulos.empresa.mocks;

import br.com.cadeiralivreempresaapi.modulos.empresa.dto.*;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ESituacaoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.enums.ETipoEmpresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Empresa;
import br.com.cadeiralivreempresaapi.modulos.empresa.model.Endereco;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.cadeiralivreempresaapi.modulos.usuario.mocks.UsuarioMocks.umUsuario;

public class EmpresaMocks {

    public static Empresa umaEmpresa() {
        return Empresa
            .builder()
            .id(1)
            .cpfCnpj("82.765.926/0001-32")
            .dataCadastro(LocalDateTime.now())
            .nome("Empresa 01")
            .razaoSocial("Empresa 01")
            .situacao(ESituacaoEmpresa.ATIVA)
            .socios(Stream.of(umUsuario()).collect(Collectors.toList()))
            .tipoEmpresa(ETipoEmpresa.BARBEARIA)
            .tempoRefreshCadeiraLivre(10)
            .build();
    }

    public static EmpresaRequest umaEmpresaRequest() {
        return EmpresaRequest
            .builder()
            .id(1)
            .cpfCnpj("82.765.926/0001-32")
            .nome("Empresa 01")
            .razaoSocial("Empresa 01")
            .tipoEmpresa(ETipoEmpresa.BARBEARIA)
            .tempoRefreshCadeiraLivre(10)
            .enderecos(Collections.singletonList(umEnderecoRequest()))
            .build();
    }

    public static EmpresaPageResponse umaEmpresaPageResponse() {
        return EmpresaPageResponse
            .builder()
            .id(1)
            .cpfCnpj("82.765.926/0001-32")
            .nome("Empresa 01")
            .tipoEmpresa(ETipoEmpresa.BARBEARIA)
            .situacao(ESituacaoEmpresa.ATIVA)
            .build();
    }

    public static EmpresaResponse umaEmpresaResponse() {
        return EmpresaResponse
            .builder()
            .id(1)
            .cpfCnpj("82.765.926/0001-32")
            .dataCadastro(LocalDateTime.now())
            .nome("Empresa 01")
            .razaoSocial("Empresa 01")
            .situacao(ESituacaoEmpresa.ATIVA)
            .proprietarioSocios(List.of(umProprietarioSocioResponse()))
            .tipoEmpresa(ETipoEmpresa.BARBEARIA)
            .build();
    }

    public static ProprietarioSocioResponse umProprietarioSocioResponse() {
        return ProprietarioSocioResponse.of(umUsuario());
    }

    public static EnderecoRequest umEnderecoRequest() {
        return EnderecoRequest
            .builder()
            .bairro("Gleba Fazenda Palhano")
            .cep("86050523")
            .cidade("Londrina")
            .estado("pr")
            .numeroRua("80")
            .complemento("Apto 888")
            .rua("Tereza Zanetti Lopes")
            .build();
    }

    public static Endereco umEndereco() {
        return Endereco
            .builder()
            .id(1)
            .bairro("Gleba Fazenda Palhano")
            .cep("86050523")
            .cidade("Londrina")
            .estado("pr")
            .numeroRua("80")
            .complemento("Apto 888")
            .rua("Tereza Zanetti Lopes")
            .build();
    }

    public static EnderecoResponse umEnderecoResponse() {
        return EnderecoResponse
            .builder()
            .id(1)
            .bairro("Gleba Fazenda Palhano")
            .cep("86050523")
            .cidade("Londrina")
            .estado("pr")
            .numeroRua("80")
            .complemento("Apto 888")
            .rua("Tereza Zanetti Lopes")
            .build();
    }
}
