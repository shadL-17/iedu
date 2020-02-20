package cn.shadl.iedubasezuul;

import cn.shadl.iedubasezuul.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@RestController
public class IeduBaseZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeduBaseZuulApplication.class, args);
    }

    @Bean
    public LoginFilter accessTokenFilter() {
        return new LoginFilter();
    }

    @RequestMapping("/sso/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "login page: "+username+", "+password;
    }

}
