package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caiyq
 */
@Service
public class UserService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;

    /**
     * 正常登录，判断用户名密码是否匹配(0学生，1教师，2管理员)
     * @param account
     * @param password
     * @param type
     */
    public Map<String,Object> login(String account,String password,int type){
        Map<String,Object> resultMap=new HashMap<>();
        User user;
        if(type==2)
        {
            user = teacherDao.getAdminByAccount(account);
            if(user!=null)
            {
                if (user.getPassword().equals(password))
                {
                    resultMap.put("message", 1);
                    //生成Jwt，放在map中返回***
                }
                else
                    resultMap.put("message", -1);
            }
            else
                resultMap.put("message", -1);

        }
        else {
            if (type == 1)
                user = teacherDao.getUserByAccount(account);
            else
                user = studentDao.getUserByAccount(account);
            if (user != null) {
                //初始密码为“123456
                if (user.getActive() == 0 && "123456".equals(password))
                    //未激活，且初始密码正确，跳转到激活界面
                    resultMap.put("message", 0);
                else {   //已激活，验证输入密码是否正确
                    if (user.getActive() == 1) {
                        if (user.getPassword().equals(password)) {
                            resultMap.put("message", 1);
                            //生成Jwt，放在map中返回***
                        } else
                            resultMap.put("message", -1);
                    } else//未激活且密码不正确的，刷新登录界面
                        resultMap.put("message", -1);
                }
            } else
                resultMap.put("message", -1);
        }
        //message返回1，登录成功，并且返回jwt；返回0，初次登录，跳转到激活页面；返回-1，用户名或密码错误
        return resultMap;
    }

    /**
     * 教师激活账号
     * @param id
     * @param password
     * @return
     */
    public boolean teacherActive(BigInteger id, String password){
        return teacherDao.activeById(id,password);
    }

    /**
     * 学生激活账号
     * @param id
     * @param password
     * @param email
     * @return
     */
    public boolean studentActive(BigInteger id,String password,String email){
        return studentDao.activeById(id,password,email);
    }

    /**
     * 根据id获取个人信息
     * @param id
     * @param type
     */
    public User getInformation(BigInteger id,int type)
    {
        User user;
        if(type==1)
            user=teacherDao.getUserById(id);
        else
            user=studentDao.getStudentByStudentId(id);
        return user;
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

    public boolean sendPasswordToEmail(String account,int type)
    {
        User user;
        if(type==1)
            user=teacherDao.getUserByAccount(account);
        else
            user=studentDao.getUserByAccount(account);
        //用户不存在或未激活
        if(user==null||user.getActive()==0){
            return false;
        }
        System.out.println("发送到邮箱 ");
        System.out.println(user.toString());
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(from);
        //接收者，判断是什么邮箱，不同邮箱发送不同！！！实现163, qq, xmu三种
        mainMessage.setTo(user.getEmail());
        //发送的标题
        mainMessage.setSubject("密码查询");
        //发送的内容
        mainMessage.setText("账户"+user.getAccount()+"的密码为："+user.getPassword());
        jms.send(mainMessage);
        return true;
    }

    /**
     * 根据id修改密码
     * @param id
     * @param newPassword
     * @param type
     * @return
     */
    public boolean modifyPassword(BigInteger id, String newPassword, int type)
    {
        Map<String,Object> resultMap=new HashMap<>();
        boolean success=false;
        if(type==1)
            success=teacherDao.setPasswordById(id,newPassword);
        else
            success=studentDao.setPasswordById(id,newPassword);
        if(success)
            return true;
        else
            return false;
}

    /**
     * 根据id修改邮箱
     * @param id
     * @param newEmail
     * @param type
     * @return
     */
    public boolean modifyEmail(BigInteger id, String newEmail,int type)
    {
        Map<String,Object> resultMap=new HashMap<>();
        boolean success=false;
        if(type==1)
            success=teacherDao.setEmailById(id,newEmail);
        else
            success=studentDao.setEmailById(id,newEmail);
        if(success)
            return true;
        else
            return false;
    }
}