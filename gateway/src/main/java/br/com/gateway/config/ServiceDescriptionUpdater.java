package br.com.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceDescriptionUpdater {
    private final DiscoveryClient discoveryClient;
    private final ServiceDefnContxt definitionContext;
    private final RestTemplate template;


//    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerConfigurations() {
        log.info("refreshSwaggerConfigurations");
        discoveryClient.getServices().forEach(serviceId -> {
            try {
                // Não deve exibir as apis do gateway
                if (!"api-gateway".equals(serviceId) && !"cloud-config-server".equals(serviceId) && !"config-server".equals(serviceId)) {
                    log.info("serviceId: {}", serviceId);
                    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
                    if (serviceInstances == null || serviceInstances.isEmpty()) {
                        log.warn("No instances available for service : {} " + serviceId);
                    } else {
                        ServiceInstance instance = serviceInstances.get(0);
                        String swaggerURL = instance.getUri() + "/v3/api-docs/";
                        log.info("swaggerURL: {}", swaggerURL);
                        Object jsonData = template.getForObject(swaggerURL, Object.class);
                        String content = getJSON(serviceId, jsonData);
                        definitionContext.addServiceDefinition(serviceId, content);
                    }
                }
            } catch (Exception e) {
                log.error("serviceId: {} sem api do swagger", serviceId);
            }
        });
    }

    public String getJSON(String serviceId, Object jsonData) {
        try {
            // Remove o servers pra fazer a requisição diretamente ao gateway
            Map<Object, Object> map = (Map<Object, Object>) jsonData;
            if (map.get("servers") != null) {
                map.remove("servers");
            }
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            log.error(serviceId, e.getMessage(), e);
            return "";
        }
    }

}
