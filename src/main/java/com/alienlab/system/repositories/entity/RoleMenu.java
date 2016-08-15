package com.alienlab.system.repositories.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 橘 on 2016/8/4.
 */
@Entity
@Table(name="tb_role_menu")
public class RoleMenu implements Serializable{
    @Id
    @Column(name="role_menu_id")
    private Long rolemenuid;
    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    public Long getRolemenuid() {
        return rolemenuid;
    }

    public void setRolemenuid(Long rolemenuid) {
        this.rolemenuid = rolemenuid;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
