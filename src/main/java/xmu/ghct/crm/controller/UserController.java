package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.ghct.crm.service.UserService;

import java.util.HashMap;
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
     * 登录操作，传入的参数有id,密码，用户身份，查找用户信息，比对密码
     * @param inMap
     * @return
     */
    @RequestMapping(value="/login")
    public Map login(@RequestBody Map<String,Object> inMap)
    {
        Map<String,Object> outMap=new HashMap<>();
        outMap.put("message","200");
        return outMap;
    }

    /**
     * 激活账号
     */
    @RequestMapping(value="/active")
    public void active(){};

    /**
     * 根据用户Id获取个人信息
     */
    @RequestMapping(value="/information")
    public void getInformation(){};

    /**
     * 修改账户密码
     */
    @RequestMapping(value="/password")
    public void modifyPassword(){};

    /**
     * 修改邮箱
     */
    @RequestMapping(value="/email")
    public void modifyEmail(){};
}
