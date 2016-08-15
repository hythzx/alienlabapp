package com.alienlab.system.repositories.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 橘 on 2016/8/3.
 */
@Entity
@Table(name="tb_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -947370114690078596L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userid;
	
    @Column(name="user_loginname")
    private String loginname;
    @Column(name="user_pwd")
    private String password;
    @Column(name="user_name")
    private String username;
    @Column(name="user_createtime",columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    @Column(name="user_lastlogin")
	@Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin;
    @Column(name="user_status")
    private String status;
    @Column(name="user_purview")
    private String purview;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurview() {
        return purview;
    }

    public void setPurview(String purview) {
        this.purview = purview;
    }
}
