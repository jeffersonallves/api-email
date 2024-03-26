package dev.jefferson.email.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("API Email")
                        .version("v1")
                        .description(
                                " \n   API para envio de email baseado na especificação OpenAPI 3.0. Nesta API poderá enviar e-mail simples, com anexo, por template HTML, além de consultar os e-mail enviados. \n" +
                                        " \n   _Para mais detalhes sobre a especificação da API, then click [here](https://www.youtube.com/channel/UCxC0oxNsOs3MivUhVbxtl4A)._ \n" +
                                        " \n   Alguns links úteis: \n" +
                                        " \n  - [O repositório da API Email](https://github.com/jeffersonallves/api-email) \n" +
                                        " \n  - [The source API definition for the Pet Store](https://github.com/jeffersonallves/api-email/dev/jefferson/email/resources/openapi.yaml) \n" +
                                        " \n  - [Playlist que documenta a criação da API Email](https://www.youtube.com/channel/UCxC0oxNsOs3MivUhVbxtl4A)")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().url("https://github.com/jeffersonallves/most-used-words")))
                .externalDocs(new ExternalDocumentation()
                        .description("Find out more about Swagger")
                        .url("http://swagger.io"));
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("api-v1")
                .pathsToMatch("/v1/**")
                .build();
    }
}
