package cn.shadl.ieduusersso.service;

import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.ieduusersso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SSOService {

    @Autowired
    private UserRepository userRepository;

    public String getAccessToken() {
        return null;
    }

    public User login(String username, String password) {
        //return userRepository.findByUserNameAndPassword().get(0);
        return null;
    }
}
