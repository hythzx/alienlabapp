package com.alienlab.system.repositories.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 橘 on 2016/8/3.
 */
@Entity
@Table(name="tb_role")
public class Role implements Serializable{
    @Id
    @Column(name="role_id")
    private Long roleid;
    @Column(name="role_name")
    private String rolename;
    @Column(name="role_index")
    private String roleindex;

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleindex() {
        return roleindex;
    }

    public void setRoleindex(String roleindex) {
        this.roleindex = roleindex;
    }
}
