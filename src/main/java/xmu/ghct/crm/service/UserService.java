package xmu.ghct.crm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.LoginUserVO;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public LoginUserVO getUserByUserAccount(String account)
    {
        User user=teacherDao.getAdminByAccount(account);
        LoginUserVO userVO=new LoginUserVO();
        if(user!=null)
        {
            BeanUtils.copyProperties(user,userVO);
            System.out.println("admin"+userVO);
            userVO.setRole("admin");
        }
        else {
            user=teacherDao.getTeacherByAccount(account);
            if(user!=null)
            {
                BeanUtils.copyProperties(user,userVO);
                System.out.println("teacher"+userVO);
                userVO.setRole("teacher");
            }
            else
            {
                user=studentDao.getStudentByAccount(account);
                if(user!=null)
                {
                    BeanUtils.copyProperties(user,userVO);
                    System.out.println("student"+userVO);
                    userVO.setRole("student");
                }
            }
        }
        if(userVO==null)
            return null;
        else
            return userVO;
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
     * @param role
     */
    public User getInformation(BigInteger id,String role)
    {
        User user;
        if(role.equals("teacher"))
            user=teacherDao.getTeacherById(id);
        else
            user=studentDao.getStudentById(id);
        return user;
    }

    /**
     * 忘记密码，向邮箱发送密码
     * @param account
     */
    @Autowired
    JavaMailSender jms;
    @Value("${spring.mail.username}")  //发送人的邮箱
    private String from;

    public boolean sendPasswordToEmail(String account)
    {
        User user=teacherDao.getTeacherByAccount(account);
        if(user==null)
            user=studentDao.getStudentByAccount(account);
        //用户不存在
        if(user==null){
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
        mainMessage.setSubject("课程管理系统——密码查询");
        //发送的内容
        mainMessage.setText("您好，您的账户"+user.getAccount()+"的密码为："+user.getPassword());
        jms.send(mainMessage);
        return true;
    }

    /**
     * 根据id修改密码
     * @param id
     * @param newPassword
     * @param role
     * @return
     */
    public boolean modifyPassword(BigInteger id, String newPassword, String role)
    {
        boolean success=false;
        if(role.equals("teacher"))
            success=teacherDao.setPasswordById(id,newPassword);
        else
            success=studentDao.setPasswordById(id,newPassword);
        return success;
}

    /**
     * 根据id修改邮箱
     * @param id
     * @param newEmail
     * @param role
     * @return
     */
    public boolean modifyEmail(BigInteger id, String newEmail,String role)
    {
        Map<String,Object> resultMap=new HashMap<>();
        boolean success=false;
        if(role.equals("teacher"))
            success=teacherDao.setEmailById(id,newEmail);
        else
            success=studentDao.setEmailById(id,newEmail);
        if(success)
            return true;
        else
            return false;
    }
}