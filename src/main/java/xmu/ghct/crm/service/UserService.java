package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.UserDao;
import xmu.ghct.crm.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caiyq
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 正常登录，判断用户名密码是否匹配
     * @param account
     * @param password
     * @param isTeacher
     */
    public Map<String,Object> login(String account,int isTeacher,String password){
        Map<String,Object> resultMap=new HashMap<>();
        User user=userDao.getUserByAccount(account,isTeacher);
        if(user!=null)
        {
            if(user.getActive()==0 && "123456".equals(password))
                //未激活，且初始密码正确，跳转到激活界面
                resultMap.put("message","0");
            else
            {   //已激活，验证输入密码是否正确
                if(user.getActive()==1)
                {
                    if(user.getPassword().equals(password))
                    {
                        resultMap.put("message", "1");
                        //生成Jwt，放在map中返回***
                    }
                    else
                        resultMap.put("message","-1");
                }
                else//未激活且密码不正确的，刷新登录界面
                    resultMap.put("message","-1");
            }
        }
        else
            resultMap.put("message","-1");
        //message返回1，登录成功，并且返回jwt；返回0，初次登录，跳转到激活页面；返回-1，用户名或密码错误
        return resultMap;
    }

    /**
     * 初次登录要激活账号，设置对应密码，填写邮箱
     * @param account
     * @param password
     * @param email
     * @param isTeacher
     */
    public Map<String,Object> active(String account, String password, String email,int isTeacher){
        User user=new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setTeacher(isTeacher);

        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.activeByAccount(user))
            resultMap.put("message","1");
        else resultMap.put("message","0");
        return resultMap;
    }

    /**
     * 根据account获取个人信息
     * @param account
     * @param isTeacher
     */
    public User getInformation(String account,int isTeacher)
    {
        return userDao.getUserByAccount(account,isTeacher);
    }

    /**
     * 忘记密码，向邮箱发送密码
     * @param account
     * @param isTeacher
     */
    public void sendPasswordToEmail(String account,int isTeacher)
    {
        User user=userDao.getUserByAccount(account,isTeacher);
        System.out.println("发送到邮箱");
        //查询到邮箱，和账户密码，向用户邮箱发邮件！！！
    }

    /**
     * 根据account修改密码
     * @param user
     * @return
     */
    public Map<String,Object> modifyPassword(User user)
    {
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setPasswordByAccount(user))
            resultMap.put("message","1");
        else
            resultMap.put("message","0");
        return resultMap;
    }

    /**
     * 根据account修改邮箱
     * @param user
     * @return
     */
    public Map<String,Object> modifyEmail(User user)
    {
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setEmailByAccount(user))
            resultMap.put("message","1");
        else
            resultMap.put("message","0");
        return resultMap;
    }
}