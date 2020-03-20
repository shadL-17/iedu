package cn.shadl.ieduservicecourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"cn.shadl"})
public class IeduServiceCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduServiceCourseApplication.class, args);
    }

}
