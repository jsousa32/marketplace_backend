package com.example.marketpalce.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class SwaggerConfiguration
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Configuration
public class SwaggerConfiguration {

    @Value("${env.keycloak.urlBase}")
    private String url;

    @Value("${env.keycloak.user.realm}")
    private String realm;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                                                .addSecuritySchemes("Autenticação IntegraNFe", createOAuthSchema()))
                .addSecurityItem(new SecurityRequirement().addList("Autenticação IntegraNFe"))
                .info(new Info().title("Marketplace Swagger")
                              .description("Marketplace Application Swagger")
                              .contact(new Contact()
                                               .url("https://marketplace.com.br/")
                                               .email("jsousa.quimico@gmail.com.br")
                                               .name("Marketplace"))
                              .version("1.0")
                     );
    }

    private SecurityScheme createOAuthSchema() {
        OAuthFlows oAuthFlows = createOAuthFlows();
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(oAuthFlows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow oAuthFlow = createAuthorizationCodeFlow();
        return new OAuthFlows().implicit(oAuthFlow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl(url + "realms/" + realm + "/protocol/openid-connect/auth");

    }
}
