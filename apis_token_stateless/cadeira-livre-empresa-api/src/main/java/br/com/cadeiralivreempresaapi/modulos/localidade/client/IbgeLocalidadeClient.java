package br.com.cadeiralivreempresaapi.modulos.localidade.client;

import br.com.cadeiralivreempresaapi.modulos.localidade.dto.CidadeResponse;
import br.com.cadeiralivreempresaapi.modulos.localidade.dto.EstadoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
    name = "ibgeLocalidadeClient",
    contextId = "ibgeLocalidadeClient",
    url = "${app-config.services.ibge-localidade.url}"
)
public interface IbgeLocalidadeClient {

    @GetMapping
    List<EstadoResponse> consultarEstados();

    @GetMapping("{estadoUf}/municipios")
    List<CidadeResponse> consultarCidadesPorEstadoUf(@PathVariable String estadoUf);
}
