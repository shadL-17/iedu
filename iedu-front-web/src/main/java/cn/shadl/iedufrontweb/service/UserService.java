package cn.shadl.iedufrontweb.service;

import cn.shadl.ieducommonbeans.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@FeignClient("iedu-service-user")
public interface UserService {
    @RequestMapping("/user-service/findByUsername")
    public List<User> findByUsername();
}
