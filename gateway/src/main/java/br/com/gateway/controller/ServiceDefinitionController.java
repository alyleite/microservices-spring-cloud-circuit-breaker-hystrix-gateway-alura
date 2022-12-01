package br.com.gateway.controller;

import br.com.gateway.config.ServiceDefnContxt;
import br.com.gateway.config.ServiceDescriptionUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ServiceDefinitionController {

    public final ServiceDefnContxt context;
    public final ServiceDescriptionUpdater serviceDescriptionUpdater;

    @GetMapping("/service/{serviceName}")
    public String getServiceDefinition(@PathVariable("serviceName") String serviceName) {
        return context.getServiceDefinition(serviceName);
    }

    @GetMapping("/service/reload/now")
    public ResponseEntity<?> reload() {
        serviceDescriptionUpdater.refreshSwaggerConfigurations();
        return ResponseEntity.status(HttpStatus.OK).body("reloaded");
    }
}
