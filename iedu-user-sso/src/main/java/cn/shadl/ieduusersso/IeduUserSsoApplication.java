package cn.shadl.ieduusersso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.shadl"})
public class IeduUserSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduUserSsoApplication.class, args);
    }

}
