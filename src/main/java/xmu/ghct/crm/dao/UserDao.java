package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.UserMapper;

/**
 * @author caiyq
 */
@Component
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户account和身份查询用户
     * @param account
     * @param isTeacher
     * @return
     */
    public User getUserByAccount(String account, boolean isTeacher)
    {
        User resultUser;
        if(isTeacher)
            resultUser=userMapper.getTeacherByAccount(account);
        else
            resultUser=userMapper.getStudentByAccount(account);
        return resultUser;
    }

    /**
     * 根据account激活用户
     * @param user
     * @return
     */
    public boolean activeByAccount(User user)
    {
        if(user.isTeacher()){
            userMapper.setTeacherActiveByAccount(user.getAccount());
            userMapper.setTeacherPasswordByAccount(user.getAccount(),user.getPassword());
            userMapper.setTeacherEmailByAccount(user.getAccount(),user.getEmail());
        }
        else{
            userMapper.setStudentActiveByAccount(user.getAccount());
            userMapper.setStudentPasswordByAccount(user.getAccount(),user.getPassword());
            userMapper.setStudentEmailByAccount(user.getAccount(),user.getEmail());
        }
        return true;
    }

    /**
     * 根据account修改密码
     * @param user
     * @return
     */
    public boolean setPasswordByAccount(User user)
    {
        if(user.isTeacher())
            userMapper.setTeacherPasswordByAccount(user.getAccount(),user.getPassword());
        else
            userMapper.setStudentPasswordByAccount(user.getAccount(),user.getPassword());
        return true;
    }

    public boolean setEmailByAccount(User user)
    {
        if(user.isTeacher())
            userMapper.setTeacherEmailByAccount(user.getAccount(),user.getEmail());
        else
            userMapper.setStudentEmailByAccount(user.getAccount(),user.getEmail());
        return true;
    }
}
