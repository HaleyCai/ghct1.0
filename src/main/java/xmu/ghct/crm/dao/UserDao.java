package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.UserMapper;

import java.math.BigInteger;

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
}
