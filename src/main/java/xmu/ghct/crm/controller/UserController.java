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

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,Object> inMap)
    {
        return userService.login((String)inMap.get("account"),(int)inMap.get("isTeacher"),(String)inMap.get(("password")));
        //如果登录成功，生成jwt，加入map，验证
    }

    /**
     * 初次登录会激活账号
     * @param inMap
     * @return
     */
    @RequestMapping(value="/active",method = RequestMethod.PUT)
    public Map<String,Object> active(@RequestBody Map<String,Object> inMap){
        return userService.active((String)inMap.get("account"),(String)inMap.get(("password")),(String)inMap.get("email"),(int)inMap.get("isTeacher"));
    }

    /**
     * 根据account查询个人信息，前端传参account,isTeacher
     * @param inMap
     * @return
     */
    @RequestMapping(value="/information",method = RequestMethod.GET)
    public User getInformation(@RequestBody Map<String,Object> inMap){
        return userService.getInformation((String)inMap.get("account"),(int)inMap.get("isTeacher"));
    }

    /**
     * 根据account修改密码，前端传参account,password,isTeacher
     * @param inMap
     * @return
     */
    @RequestMapping(value="/password",method = RequestMethod.PUT)
    public Map<String,Object> modifyPassword(@RequestBody Map<String,Object> inMap){
        User user=new User();
        user.setAccount((String)inMap.get("account"));
        user.setPassword((String)inMap.get("password"));
        user.setTeacher((int)inMap.get("isTeacher"));
        return userService.modifyPassword(user);
    }

    /**
     * 忘记密码时，将密码发送到用户的邮箱中
     * @param inMap
     */
    @RequestMapping(value="/password",method = RequestMethod.GET)
    public void sendPasswordToEmail(@RequestBody Map<String,Object> inMap)
    {
        userService.sendPasswordToEmail((String)inMap.get("account"),(int)inMap.get("isTeacher"));
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
        return userService.modifyEmail(user);
    }
}
