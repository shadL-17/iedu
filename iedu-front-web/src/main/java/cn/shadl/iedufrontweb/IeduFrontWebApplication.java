package cn.shadl.iedufrontweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class IeduFrontWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduFrontWebApplication.class, args);
    }

    @RequestMapping("/")
    public String hi() {
        return "login";
    }

}
