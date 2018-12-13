package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.UserDao;
import xmu.ghct.crm.entity.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 正常登录，判断用户名密码是否匹配
     * @param user
     * @param isTeacher
     * @return
     */
    public Map<String,Object> login(User user, boolean isTeacher){
        Map<String,Object> resultMap=new HashMap<>();
        if(user.getAccount()==null||user.getPassword()==null){
            resultMap.put("message","用户名或密码不能为空");
        }
        else{
            User resultUser=userDao.getUserByAccount(user.getAccount(),isTeacher);
            if(resultUser.getPassword().equals(user.getPassword())){
                resultMap.put("message","登录成功");
            }
            else
                resultMap.put("message","用户名或密码错误");
        }
        resultMap.put("kkk","调用service");
        return resultMap;
    }

    /**
     * 激活账号，设置对应密码，向邮箱发送信息
     * @param user
     * @param isTeacher
     */
    public Map<String,Object> active(User user, boolean isTeacher){
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.activeByAccount(user,isTeacher))
            resultMap.put("message","激活成功");
        else resultMap.put("message","激活失败");
        return resultMap;
    }

    /**
     * 根据account获取个人信息
     * @param account
     * @param isTeacher
     */
    public User getInformation(String account,boolean isTeacher)
    {
        return userDao.getUserByAccount(account,isTeacher);
    }

    /**
     * 根据account修改密码
     * @param user
     * @param isTeacher
     * @return
     */
    public Map<String,Object> modifyPassword(User user,boolean isTeacher)
    {
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setPasswordByAccount(user,isTeacher))
            resultMap.put("message","修改密码成功");
        else
            resultMap.put("message","修改密码失败");
        return resultMap;
    }

    public Map<String,Object> modifyEmail(User user,boolean isTeacher)
    {
        Map<String,Object> resultMap=new HashMap<>();
        if(userDao.setEmailByAccount(user,isTeacher))
            resultMap.put("message","修改邮箱成功");
        else
            resultMap.put("message","修改邮箱失败");
        return resultMap;
    }
}