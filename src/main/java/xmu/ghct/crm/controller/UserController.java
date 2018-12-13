package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * 登录操作，前端传入的参数有account,password，isTeacher，返回map，message中写了操作成功与否
     * @param inMap
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,Object> inMap)
    {
        User input=new User();
        input.setAccount((String)inMap.get("account"));
        input.setPassword((String)inMap.get("password"));
        return  userService.login(input,(boolean)inMap.get("isTeacher"));
    }

    /**
     * 激活账号
     */
    @RequestMapping(value="/active",method = RequestMethod.PUT)
    public void active(){};

    /**
     * 根据account获取个人信息
     */
    @RequestMapping(value="/information")
    public void getInformation(){};

    /**
     * 根据account修改账户密码
     */
    @RequestMapping(value="/password")
    public void modifyPassword(){};

    /**
     * 根据account修改邮箱
     */
    @RequestMapping(value="/email")
    public void modifyEmail(){};
}
