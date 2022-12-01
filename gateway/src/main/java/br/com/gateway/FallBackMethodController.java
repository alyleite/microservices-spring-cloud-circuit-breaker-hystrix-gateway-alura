package br.com.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        return "{\"error\": \"User Service is taking longer than Expected. Please try again later\" }";
    }

    @GetMapping("/departmentServiceFallBack")
    public String departmentServiceFallBackMethod() {
        return "{\"error\": \"Department Service is taking longer than Expected. Please try again later\" }";
    }

    @GetMapping("/stoneServiceFallBack")
    public String stoneServiceFallBackMethod() {
        return "{\"error\": \"Stone Service is taking longer than Expected. Please try again later\" }";
    }

    @GetMapping("/accountServiceFallBack")
    public String accountServiceFallBackMethod() {
        return "{\"error\": \"Account Service is taking longer than Expected. Please try again later\" }";
    }

    @GetMapping("/pixServiceFallBack")
    public String pixServiceFallBackMethod() {
        return "{\"error\": \"Pix Service is taking longer than Expected. Please try again later\" }";
    }

    @GetMapping("/notificationServiceFallBack")
    public String notificationServiceFallBack() {
        return "{\"error\": \"Notification Service is taking longer than Expected. Please try again later\" }";
    }
}
