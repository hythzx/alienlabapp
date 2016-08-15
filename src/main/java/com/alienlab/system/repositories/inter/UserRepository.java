package com.alienlab.system.repositories.inter;

import com.alienlab.system.repositories.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 橘 on 2016/8/3.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findUserByLoginname(String username);
}
