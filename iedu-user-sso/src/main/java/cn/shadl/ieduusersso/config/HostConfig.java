package cn.shadl.ieduusersso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HostConfig {
    @Value("${host.ip}")
    private String ip;

    public String getIp() {
        return ip;
    }
}
