package cn.shadl.ieduserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"cn.shadl"})
public class IeduServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduServiceUserApplication.class, args);
    }

}
