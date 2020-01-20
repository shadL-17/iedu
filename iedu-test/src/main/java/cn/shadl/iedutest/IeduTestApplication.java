package cn.shadl.iedutest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/test")
public class IeduTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduTestApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello this is iedu-test client.";
    }

}
