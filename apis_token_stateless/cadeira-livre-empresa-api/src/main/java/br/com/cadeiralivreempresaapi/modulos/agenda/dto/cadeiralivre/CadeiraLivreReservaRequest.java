package br.com.cadeiralivreempresaapi.modulos.agenda.dto.cadeiralivre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadeiraLivreReservaRequest {

    private String token;
    private String cartaoId;
    private Integer cadeiraLivreId;
}
