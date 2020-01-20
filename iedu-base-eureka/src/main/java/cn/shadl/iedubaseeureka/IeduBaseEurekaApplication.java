package cn.shadl.iedubaseeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class IeduBaseEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduBaseEurekaApplication.class, args);
    }

}
