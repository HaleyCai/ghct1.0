package xmu.ghct.crm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.LoginUserVO;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.StudentMapper;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.SQLException;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //token在httpRequest中jwt中有id和role的值

    /**
     * 登录，前端传参account,password,type
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,Object> inMap) throws NotFoundException
    {
        String account = inMap.get("account").toString();
        String password = inMap.get("password").toString();

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(account, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginUserVO userInDatabase = userService.getUserByUserAccount(account);
        String token = jwtTokenUtil.generateToken(userInDatabase);
        Map<String, Object> map = new HashMap<>(5);
        map.put("id",userInDatabase.getId());
        map.put("token", token);
        map.put("role", userInDatabase.getRole());
        map.put("active", userInDatabase.getActive());
        return map;
    }

    @GetMapping(value = "/user/index")
    public Map<String,String> index(HttpServletRequest request) throws NotFoundException
    {
        Map<String,String> map=new HashMap<>();
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        String role=jwtTokenUtil.getRoleFromRequest(request);
        User user=userService.getInformation(id,role);
        map.put("name",user.getName());
        map.put("account",user.getAccount());
        return map;
    }

    /**
     * 教师激活账号，前端传参password，id根据jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/active",method = RequestMethod.PUT)
    public Map<String,Object> teacherActive(HttpServletRequest request,
                                            @RequestBody Map<String,Object> inMap){
        Map<String,Object> map=new HashMap<>();
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        System.out.println("id"+id);
        if(userService.teacherActive(id, inMap.get("password").toString()))
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
    public Map<String,Object> studentActive(HttpServletRequest request,
                                            @RequestBody Map<String,Object> inMap){
        Map<String,Object> map=new HashMap<>();
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        if(userService.studentActive(id, inMap.get("password").toString(),inMap.get("email").toString()))
            map.put("message",true);
        else map.put("message",false);
        return map;
    }

    /**
     * 根据id查询个人信息，前端传参account,type，id根据jwt获得
     * @return
     */
    @RequestMapping(value="/user/information",method = RequestMethod.GET)
    public User getInformation(HttpServletRequest request) throws NotFoundException{
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        String role=jwtTokenUtil.getRoleFromRequest(request);
        return userService.getInformation(
                id,
                role);
    }

    /**
     * 根据id修改密码，前端传参password，id从jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/password",method = RequestMethod.PUT)
    public boolean modifyPassword(HttpServletRequest request,@RequestBody Map<String,Object> inMap) throws SQLException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        String role=jwtTokenUtil.getRoleFromRequest(request);
        return userService.modifyPassword(
                id,
                (String)inMap.get("password"),
                role);
    }

    /**
     * 忘记密码时，将密码发送到用户的邮箱中，前端传参account，type，account根据jwt获得
     */
    @RequestMapping(value="/user/forgetpassword",method = RequestMethod.GET)
    public boolean sendPasswordToEmail(@RequestParam String account)
    {
        return userService.sendPasswordToEmail(account);
    }

    /**
     * 根据id修改邮箱，前端传参id，email，type，id根据jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/user/email",method = RequestMethod.PUT)
    public boolean modifyEmail(HttpServletRequest request,@RequestBody Map<String,Object> inMap) throws SQLException{
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        String role=jwtTokenUtil.getRoleFromRequest(request);
        return userService.modifyEmail(
                id,
                (String)inMap.get("email"),
                role);
    }
}
