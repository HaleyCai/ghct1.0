package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.StudentMapper;
import xmu.ghct.crm.service.UserService;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caiyq
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    StudentMapper studentMapper;

    //***token做好后，放到httpRequest中，每个函数可以获得，jwt中有userId和type的值，现在先假定从前端传入

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
     * 教师激活账号，前端传参password，id根据jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/active",method = RequestMethod.PUT)
    public Map<String,Object> teacherActive(@RequestBody Map<String,Object> inMap){
        Map<String,Object> map=new HashMap<>();
        if(userService.teacherActive(
                new BigInteger(inMap.get("id").toString()),
                inMap.get("password").toString()))
            map.put("message",true);
        else map.put("message",false);
        return map;
    }

    /**
     * 学生激活账号，前端传参password，email，id根据jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/student/active",method = RequestMethod.PUT)
    public Map<String,Object> studentActive(@RequestBody Map<String,Object> inMap){
        Map<String,Object> map=new HashMap<>();
        if(userService.studentActive(new BigInteger(inMap.get("id").toString()), inMap.get("password").toString(),inMap.get("email").toString()))
            map.put("message",true);
        else map.put("message",false);
        return map;
    }

    /**
     * 根据id查询个人信息，前端传参account,type，id根据jwt获得
     * @return
     */
    @RequestMapping(value="/user/information",method = RequestMethod.GET)
    public User getInformation(@RequestParam BigInteger id,@RequestParam int type){
        return userService.getInformation(
                id,
                type);
    }

    /**
     * 根据id修改密码，前端传参id,password,type，id从jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/password",method = RequestMethod.PUT)
    public boolean modifyPassword(@RequestBody Map<String,Object> inMap){
        return userService.modifyPassword(
                new BigInteger(inMap.get("id").toString()),
                (String)inMap.get("password"),
                (int)inMap.get("type"));
    }

    /**
     * 忘记密码时，将密码发送到用户的邮箱中，前端传参account，type，account根据jwt获得
     */
    @RequestMapping(value="/user/password",method = RequestMethod.GET)
    public boolean sendPasswordToEmail(@RequestParam String account,@RequestParam int type)
    {
        return userService.sendPasswordToEmail(
                account,
                type);
    }

    /**
     * 根据id修改邮箱，前端传参id，email，type，id根据jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/email",method = RequestMethod.PUT)
    public boolean modifyEmail(@RequestBody Map<String,Object> inMap){
        return userService.modifyEmail(
                new BigInteger(inMap.get("id").toString()),
                (String)inMap.get("email"),
                (int)inMap.get("type"));
    }
}
