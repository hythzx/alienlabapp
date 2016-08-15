package com.alienlab.system.repositories.inter;

import com.alienlab.system.repositories.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 橘 on 2016/8/3.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findMenusByMenutypeOrderBySortAsc(String type);
}
