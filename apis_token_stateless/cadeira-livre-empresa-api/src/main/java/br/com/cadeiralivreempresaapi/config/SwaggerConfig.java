package br.com.cadeiralivreempresaapi.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String OAUTH2 = "oauth2";
    private static final String HEADER = "header";
    private static final String GLOBAL_SCOPE = "global";
    private static final String ACCESS_EVERYTHING = "accessEverything";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Lists.newArrayList(apiKey()))
            .securityContexts(Lists.newArrayList(securityContext()));
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext
            .builder()
            .securityReferences(getOAuth2Authentication())
            .forPaths(PathSelectors.any())
            .build();
    }

    private List<SecurityReference> getOAuth2Authentication() {
        return Lists.newArrayList(new SecurityReference(OAUTH2,
            new AuthorizationScope[]{
                new AuthorizationScope(GLOBAL_SCOPE, ACCESS_EVERYTHING)
            })
        );
    }

    private ApiKey apiKey() {
        return new ApiKey(OAUTH2, AUTHORIZATION_HEADER, HEADER);
    }
}