package cn.shadl.ieduserviceuser.service;

import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.ieduserviceuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUid(Integer uid) {
        List<User> users = userRepository.findByUid(uid);
        return (users!=null&&!users.isEmpty()) ? users.get(0) : null;
    }

    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
