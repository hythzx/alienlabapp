package com.alienlab.system.repositories.inter;

import com.alienlab.system.repositories.entity.User;
import com.alienlab.system.repositories.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 橘 on 2016/8/4.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long>{
    List<UserRole> findUserrolesByUser(User u);
}
