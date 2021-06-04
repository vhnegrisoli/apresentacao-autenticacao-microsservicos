package br.com.b2vnauthapi.b2vnauthapi.modules.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    contextId = "usuarioClient",
    name = "usuarioAutenticadoClient",
    url = "${app-config.services.b2vn-radar-api.url}")
public interface UsuarioClient {

}
