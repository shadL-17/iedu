package cn.shadl.ieduusersso.repository;

import cn.shadl.ieducommonbeans.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findByUserNameAndPassword();
}
