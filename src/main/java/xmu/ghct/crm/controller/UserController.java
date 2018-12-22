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
public class UserController {

    @Autowired
    private UserService userService;

    //***token做好后，放到httpRequest中，每个函数可以获得，jwt中有account和isTeacher的值，现在先假定从前端传入

    /**
     * 登录，前端传参account,password,type
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,Object> inMap)
    {
        return userService.login(
                (String)inMap.get("account"),
                (String)inMap.get(("password")),
                (int)inMap.get("type"));
        //如果登录成功，生成jwt，加入map，验证
    }

    /**
     * 初次登录会激活账号，前端传参account，password，email
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/active",method = RequestMethod.PUT)
    public boolean teacherActive(@RequestBody Map<String,Object> inMap){
        return userService.active(
                (String)inMap.get("account"),
                (String)inMap.get(("password")),
                (String)inMap.get("email"),
                1);
    }

    @RequestMapping(value="/student/active",method = RequestMethod.PUT)
    public boolean studentActive(@RequestBody Map<String,Object> inMap){
        return userService.active(
                (String)inMap.get("account"),
                (String)inMap.get(("password")),
                (String)inMap.get("email"),
                2);
    }

    /**
     * 根据account查询个人信息，前端传参account,type
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/information",method = RequestMethod.GET)
    public User getInformation(@RequestBody Map<String,Object> inMap){
        return userService.getInformation(
                (String)inMap.get("account"),
                (int)inMap.get("type"));
    }

    /**
     * 根据account修改密码，前端传参account,password,type
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/password",method = RequestMethod.PUT)
    public boolean modifyPassword(@RequestBody Map<String,Object> inMap){
        return userService.modifyPassword(
                (String)inMap.get("account"),
                (String)inMap.get("password"),
                (int)inMap.get("type"));
    }

    /**
     * 忘记密码时，将密码发送到用户的邮箱中，前端传参account，type
     * @param inMap
     */
    @RequestMapping(value="/user/password",method = RequestMethod.GET)
    public void sendPasswordToEmail(@RequestBody Map<String,Object> inMap)
    {
        userService.sendPasswordToEmail(
                (String)inMap.get("account"),
                (int)inMap.get("type"));
    }

    /**
     * 根据account修改邮箱，前端传参account，email，type
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/email",method = RequestMethod.PUT)
    public boolean modifyEmail(@RequestBody Map<String,Object> inMap){
        return userService.modifyEmail(
                (String)inMap.get("account"),
                (String)inMap.get("email"),
                (int)inMap.get("type"));
    }
}
