package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.service.UserService;

import java.util.Map;

/**
 * @author caiyq
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    //***token做好后，放到httpRequest中，每个函数可以获得，jwt中有account和isTeacher的值，现在先假定从前端传入

    /**
     * 登录操作，前端传参account,password，isTeacher，返回map，message中写了操作成功与否
     * @param inMap
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,Object> inMap)
    {
        User user=new User();
        user.setAccount((String)inMap.get("account"));
        user.setPassword((String)inMap.get("password"));
        return  userService.login(user,(boolean)inMap.get("isTeacher"));
    }

    /**
     * 激活账号，前端传参account,password,email,isTeacher
     * @param inMap
     * @return
     */
    @RequestMapping(value="/active",method = RequestMethod.PUT)
    public Map<String,Object> active(@RequestBody Map<String,Object> inMap){
        System.out.println("前端传来："+inMap.get("account"));
        User user=new User();
        user.setAccount((String)inMap.get("account"));
        user.setPassword((String)inMap.get("password"));
        user.setEmail((String)inMap.get("email"));
        return userService.active(user,(boolean)inMap.get("isTeacher"));
    }

    /**
     * 根据account查询个人信息，前端传参account,isTeacher
     * @param inMap
     * @return
     */
    @RequestMapping(value="/information")
    public User getInformation(@RequestBody Map<String,Object> inMap){
        return userService.getInformation((String)inMap.get("account"),(boolean)inMap.get("isTeacher"));
    }

    /**
     * 根据account修改密码，前端传参account,password,isTeacher
     * @param inMap
     * @return
     */
    @RequestMapping(value="/password")
    public Map<String,Object> modifyPassword(@RequestBody Map<String,Object> inMap){
        User user=new User();
        user.setAccount((String)inMap.get("account"));
        user.setPassword((String)inMap.get("password"));
        return userService.modifyPassword(user,(boolean)inMap.get("isTeacher"));
    }

    /**
     * 根据account修改邮箱
     * @param inMap
     * @return
     */
    @RequestMapping(value="/email")
    public Map<String,Object> modifyEmail(@RequestBody Map<String,Object> inMap){
        User user=new User();
        user.setAccount((String)inMap.get("account"));
        user.setPassword((String)inMap.get("email"));
        return userService.modifyEmail(user,(boolean)inMap.get("isTeacher"));
    }
}
