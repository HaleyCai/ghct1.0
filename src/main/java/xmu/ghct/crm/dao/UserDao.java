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
     * @param type
     * @return
     */
    public User getUserByAccount(String account, int type)
    {
        User resultUser;
        if(type==1)
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
        int v1,v2,v3;
        if(user.getType()==1){
            v1 = userMapper.setTeacherActiveByAccount(user.getAccount());
            v2 = userMapper.setTeacherPasswordByAccount(user.getAccount(),user.getPassword());
            v3 = userMapper.setTeacherEmailByAccount(user.getAccount(),user.getEmail());
        }
        else{
            v1 = userMapper.setStudentActiveByAccount(user.getAccount());
            v2 = userMapper.setStudentPasswordByAccount(user.getAccount(),user.getPassword());
            v3 = userMapper.setStudentEmailByAccount(user.getAccount(),user.getEmail());
        }
        if(v1==1 && v2==1 && v3==1)
            return true;
        else return false;
    }

    /**
     * 根据account修改密码
     * @param user
     * @return
     */
    public boolean setPasswordByAccount(User user)
    {
        int v1;
        if(user.getType()==1)
            v1 = userMapper.setTeacherPasswordByAccount(user.getAccount(),user.getPassword());
        else
            v1 = userMapper.setStudentPasswordByAccount(user.getAccount(),user.getPassword());
        if(v1==1)
            return true;
        else
            return false;
    }

    public boolean setEmailByAccount(User user)
    {
        int v1;
        if(user.getType()==1)
            v1 = userMapper.setTeacherEmailByAccount(user.getAccount(),user.getEmail());
        else
            v1 = userMapper.setStudentEmailByAccount(user.getAccount(),user.getEmail());
        if(v1==1)
            return true;
        else
            return false;
    }
}
