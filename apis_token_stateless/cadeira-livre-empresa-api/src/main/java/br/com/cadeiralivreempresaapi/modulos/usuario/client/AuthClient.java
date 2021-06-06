package br.com.cadeiralivreempresaapi.modulos.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
    name = "authClient",
    contextId = "authClient",
    url = "${app-config.application.url}"
)
public interface AuthClient {

    @PostMapping("oauth/token")
    @SuppressWarnings("ParameterNumber")
    Optional<Object> generateAccessToken(@RequestParam("client_id") String clientId,
                                         @RequestParam("client_secret") String clientSecret,
                                         @RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("grant_type") String grantType,
                                         @RequestHeader("Content_Type") String contentType);
}
