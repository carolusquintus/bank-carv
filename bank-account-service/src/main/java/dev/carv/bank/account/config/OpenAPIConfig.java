package dev.carv.bank.account.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI bankAccountOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Accounts REST API Documentation")
                .description("Bank CARV Accounts microservice REST API Documentation")
                .version("v1")
                .contact(new Contact()
                    .name("Carlos Rosas")
                    .email("carloschars@gmail.com")
                    .url("https://carolusquintus.dev"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")))
            .externalDocs(new ExternalDocumentation()
                .description("Accounts REST API Documentation")
                .url("https://bank-carv.com/account-service/swagger-ui.html"));
    }

}
