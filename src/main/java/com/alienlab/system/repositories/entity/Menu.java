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
@Table(name="tb_menu")
public class Menu implements Serializable{
    @Id
    @Column(name="menu_id")
    private Long menuid;
    @Column(name="menu_name")
    private String menuname;
    @Column(name="menu_type")
    private String menutype;
    @Column(name="menu_pid")
    private Long pid;
    @Column(name="menu_content")
    private String content;
    @Column(name="menu_attr")
    private String attr;
    @Column(name="menu_status")
    private String status;
    @Column(name="menu_sort")
    private Integer sort;

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenutype() {
        return menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
