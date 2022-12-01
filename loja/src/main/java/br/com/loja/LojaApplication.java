package br.com.loja;

import br.com.loja.config.FeignClientExceptionErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
public class LojaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LojaApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public RequestInterceptor getInterceptorAutenticacao() {
        return requestTemplate -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) return;
            var details = (OAuth2AuthenticationDetails) authentication.getDetails();
            requestTemplate.header("Authorization", "Bearer " + details.getTokenValue());
        };
    }
}
