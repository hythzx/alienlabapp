package com.alienlab.system.repositories.inter;

import com.alienlab.system.repositories.entity.Role;
import com.alienlab.system.repositories.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 橘 on 2016/8/4.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu,Long> {
    List<RoleMenu> findRolemenusByRole(Role role);
}
