package com.alienlab.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.common.TypeUtils;
import com.alienlab.db.ExecResult;
import com.alienlab.system.repositories.entity.*;
import com.alienlab.system.repositories.inter.MenuRepository;
import com.alienlab.system.repositories.inter.RoleMenuRepository;
import com.alienlab.system.repositories.inter.UserRepository;
import com.alienlab.system.repositories.inter.UserRoleRepository;
import com.alienlab.system.service.SystemService;
import com.alienlab.utils.Azdg;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by 橘 on 2016/8/3.
 */
@RestController
public class SystemController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleMenuRepository roleMenuRepository;
    @Autowired
    SystemService systemService;

    @RequestMapping("system/dologin")
    @ResponseBody
    public String doLogin(HttpServletRequest request){
        try{
            String input=IOUtils.toString(request.getInputStream(),"utf-8");
            JSONObject param= JSON.parseObject(input);
            String loginname=param.getString("loginname");
            String pwd=param.getString("password");

            Azdg a=new Azdg();
            String password= a.encrypt(pwd);
            User user=userRepository.findUserByLoginname(loginname);
            if(user==null){
                return new ExecResult(false,"登录用户不存在").toString();
            }else{
                if(user.getPassword().equals(password)){//登录成功
                    user.setLastlogin(new Date());
                    userRepository.save(user);
                    JSONObject ju=(JSONObject) JSONObject.toJSON(user);
                    JSONArray userroles=new JSONArray();
                    String usermenus="";
                    //加载角色
                    List<UserRole> roles=userRoleRepository.findUserrolesByUser(user);
                    for(int i=0;i<roles.size();i++){
                        Role role=roles.get(i).getRole();
                        userroles.add((JSON)JSON.toJSON(role));
                        //加载角色菜单
                        List<RoleMenu> rolemenus=roleMenuRepository.findRolemenusByRole(role);
                        for(int j=0;j<rolemenus.size();j++){
                            Menu m=rolemenus.get(j).getMenu();
                            if(m.getMenutype().equals("功能")){
                                if(usermenus.indexOf(m.getStatus()+",")<0){
                                    usermenus+=m.getStatus()+",";
                                }
                            }
                        }
                    }
                    ju.put("roles",userroles);
                    ju.put("menus",usermenus);

                    request.getSession().setAttribute("user",ju);//当前用户进入session
                    ExecResult er=new ExecResult();
                    er.setResult(true);
                    er.setData(ju);
                    return er.toString();
                }else{
                    return new ExecResult(false,"用户名或密码错误").toString();
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
            return new ExecResult(false,"登录发生异常").toString();
        }
    }

    @RequestMapping("system/getmenu")
    @ResponseBody
    public String getSystemMenu(){
        List<Menu> modules=menuRepository.findMenusByMenutypeOrderBySortAsc("模块");
        List<Menu> apps=menuRepository.findMenusByMenutypeOrderBySortAsc("功能");
        JSONArray menus=new JSONArray();
        JSONObject firstmenu=new JSONObject();
        firstmenu.put("text","功能菜单");
        firstmenu.put("heading",true);
        menus.add(firstmenu);
        for(int i=0;i<modules.size();i++){
            Menu module=modules.get(i);
            JSONObject m=new JSONObject();
            m.put("text",module.getMenuname());
            m.put("sref",module.getContent());
            m.put("icon",module.getAttr());
            JSONArray submenus=new JSONArray();
            for(int j=0;j<apps.size();j++){
                Menu app=apps.get(j);
                JSONObject submenu=new JSONObject();
                submenu.put("text",app.getMenuname());
                submenu.put("sref",app.getStatus());
                if(app.getPid().longValue()==module.getMenuid().longValue()){
                    submenus.add(submenu);
                }
            }
            if(submenus.size()>0){
                m.put("submenu",submenus);
                menus.add(m);
            }
        }
        return menus.toJSONString();
    }

    @RequestMapping(value="/system/user/users",method= RequestMethod.GET)
    public String getusers(HttpServletRequest request) {
        return JSON.toJSONString(systemService.getusers());
    }

    @RequestMapping(value="/system/user/roles",method=RequestMethod.GET)
    public String getroles(HttpServletRequest request) {
        return JSON.toJSONString(systemService.getroles());
    }

    @RequestMapping(value="/sys/user/delete/{userid}",method=RequestMethod.DELETE)
    public String deleteuser(@PathVariable("userid") Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(systemService.deleteuser(id)) {
                return new ExecResult(true,"此用户删除成功").toString();
            } else {
                return new ExecResult(false,"此用户删除失败").toString();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ExecResult(false,"此用户删除过程中发生异常").toString();
        }
    }

    @RequestMapping(value="/system/user/users/{key}",method=RequestMethod.GET)
    public String searchUsers(@PathVariable("key") String key,HttpServletRequest request,HttpServletResponse response) {
        return JSON.toJSONString(systemService.searchUsers(key));
    }

    @RequestMapping(value="/system/user/add",method=RequestMethod.POST)
    public String addUser(HttpServletRequest request) {
        try {
            String jsonBody = IOUtils.toString(request.getInputStream(),"UTF-8");
            JSONObject form = JSONObject.parseObject(jsonBody);
            User user = new User();
            user.setLoginname(form.getString("loginname"));
            user.setPassword(form.getString("password"));
            user.setUsername(form.getString("username"));
            Date now = new Date();
            user.setCreatetime(now);
            user.setLastlogin(now);
            user.setPurview(form.getString("purview"));
            user.setStatus("1");

            User result = systemService.addUser(user);
            if(result == null) {
                return new ExecResult(false,"添加用户失败").toString();
            } else {
                ExecResult er = new ExecResult();
                er.setResult(true);
                er.setData((JSON)JSON.toJSON(result));
                return er.toString();
            }
        } catch(IOException e) {
            e.printStackTrace();
            return new ExecResult(false,"添加用户出现异常").toString();
        }
    }

    @RequestMapping(value="/system/user/update",method=RequestMethod.POST)
    public String updateUser(HttpServletRequest request) {
        try {
            String jsonBody = IOUtils.toString(request.getInputStream(),"UTF-8");
            JSONObject form = JSONObject.parseObject(jsonBody);
            User user = new User();
            user.setUserid(form.getLong("userid"));
            user.setLoginname(form.getString("loginname"));
            user.setPassword(form.getString("password"));
            user.setUsername(form.getString("username"));
            user.setCreatetime(TypeUtils.str2date(form.getString("createtime"), "yyyy-MM-dd hh:mm:ss"));
            user.setLastlogin(TypeUtils.str2date(form.getString("lastlogin"), "yyyy-MM-dd hh:mm:ss"));
            user.setPurview(form.getString("purview"));
            user.setStatus(form.getString("status"));

            User result = systemService.updateUser(user);
            if(result == null) {
                return new ExecResult(false,"修改用户失败").toString();
            } else {
                ExecResult er = new ExecResult();
                er.setResult(true);
                er.setData((JSON)JSON.toJSON(result));
                return er.toString();
            }

        } catch(IOException e) {
            e.printStackTrace();
            return new ExecResult(false,"修改用户出现异常").toString();
        }
    }
}
