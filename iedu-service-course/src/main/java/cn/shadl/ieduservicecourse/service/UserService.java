package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "iedu-service-user")
public interface UserService {
    @GetMapping("/findByUid")
    User findByUid(@RequestParam("uid") Integer uid);
}
