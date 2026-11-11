package dev.calvinstechsupport.inventory.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Network Device Inventory API",
        version = "v1",
        description = "A learning and portfolio API for managing routers, switches, access points, servers and other network devices.",
        contact = @Contact(name = "Calvin's Tech Support", url = "https://calvinstechsupport.dev")
))
public class OpenApiConfiguration {
}
