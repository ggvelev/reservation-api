package io.reservation.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Specifies info and security scheme for OpenAPI 3 spec and swaggerUI.
 *
 * @author Georgi Velev
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RESTaurant reservation API",
                version = "v0.1",
                description = "REST API server for restaurant reservations",
                contact = @Contact(
                        name = "Georgi Velev",
                        email = "georgi.a.velev@gmail.com",
                        url = "https://www.linkedin.com/in/velev-georgi"
                )
        )
)
@SecurityScheme(
        name = "Basic",
        description = "Basic authentication scheme (See RFC 7617)",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "Basic"
)
public class OpenApiConfig {
    // pass
}
