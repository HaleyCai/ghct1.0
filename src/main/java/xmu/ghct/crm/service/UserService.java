package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
     * @param type
     */
    public Map<String,Object> login(String account,String password,int type){
        Map<String,Object> resultMap=new HashMap<>();
        User user=userDao.getUserByAccount(account,type);
        if(user!=null)
        {
            //初始密码为“123456
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
     * @param type
     */
    public Map<String,Object> active(String account, String password, String email,int type){
        User user=new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setType(type);

        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.activeByAccount(user))
            resultMap.put("message","1");
        else resultMap.put("message","0");
        return resultMap;
    }

    /**
     * 根据account获取个人信息
     * @param account
     * @param type
     */
    public User getInformation(String account,int type)
    {
        return userDao.getUserByAccount(account,type);
    }

    /**
     * 忘记密码，向邮箱发送密码
     * @param account
     * @param type
     */
    @Autowired
    JavaMailSender jms;
    @Value("${spring.mail.username}")  //发送人的邮箱
    private String from;

    public void sendPasswordToEmail(String account,int type)
    {
        User user=userDao.getUserByAccount(account,type);
        System.out.println("发送到邮箱 ");
        System.out.println(user.toString());
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(from);
        //接收者
        mainMessage.setTo(user.getEmail());
        //发送的标题
        mainMessage.setSubject("密码查询");
        //发送的内容
        mainMessage.setText("账户"+user.getAccount()+"的密码为："+user.getPassword());
        jms.send(mainMessage);
    }

    /**
     * 根据account修改密码
     * @param account
     * @param newPassword
     * @param type
     * @return
     */
    public Map<String,Object> modifyPassword(String account, String newPassword, int type)
    {
        User user=new User();
        user.setAccount(account);
        user.setPassword(newPassword);
        user.setType(type);

        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setPasswordByAccount(user))
            resultMap.put("message","1");
        else
            resultMap.put("message","0");
        return resultMap;
    }

    /**
     * 根据account修改邮箱
     * @param account
     * @param newEmail
     * @param type
     * @return
     */
    public Map<String,Object> modifyEmail(String account, String newEmail,int type)
    {
        User user=new User();
        user.setAccount(account);
        user.setEmail(newEmail);
        user.setType(type);
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setEmailByAccount(user))
            resultMap.put("message","1");
        else
            resultMap.put("message","0");
        return resultMap;
    }
}