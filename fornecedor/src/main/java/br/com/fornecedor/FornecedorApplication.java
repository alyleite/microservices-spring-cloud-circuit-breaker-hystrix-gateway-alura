package br.com.fornecedor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableResourceServer
public class FornecedorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FornecedorApplication.class, args);
    }

}
