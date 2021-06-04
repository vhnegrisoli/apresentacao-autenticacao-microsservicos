package com.b2vnradarapi.b2vnradarapi.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class ClientCredentialsTokenService {

    @Value("${app-config.services.b2vn-auth-api.url}")
    private String b2vnAuthApiUrl;
    @Value("${app-config.oauth-clients.b2vn-radar-api.client}")
    private String oauthClient;
    @Value("${app-config.oauth-clients.b2vn-radar-api.secret}")
    private String oauthClientSecret;

    private ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(oauthClient);
        details.setClientSecret(oauthClientSecret);
        details.setScope(singletonList("b2vn-radar-api"));
        details.setAuthenticationScheme(AuthenticationScheme.form);
        details.setAccessTokenUri(b2vnAuthApiUrl + "/oauth/token");
        details.setClientAuthenticationScheme(AuthenticationScheme.header);
        return details;
    }

    public OAuth2AccessToken getToken() {
        return new ClientCredentialsAccessTokenProvider()
                .obtainAccessToken(resourceDetails(), new DefaultAccessTokenRequest());
    }
}
