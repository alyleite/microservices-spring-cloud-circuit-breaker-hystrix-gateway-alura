package br.com.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class SwaggerUIConfiguration {
    private final ServiceDefnContxt serviceDefnContxt;

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResource(InMemorySwaggerResourcesProvider provider) {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>(provider.get());
            resources.clear();
            resources.addAll(serviceDefnContxt.getSwaggerDefinitions());
            log.warn("Resources: {}", resources);
            resources.forEach(value -> log.warn("Resource: {}", value.getName()));
            return resources;
        };
    }
}
