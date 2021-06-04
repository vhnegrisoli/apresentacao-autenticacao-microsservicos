package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CobrancaRequest {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private EnderecoCobrancaRequest endereco;
}
