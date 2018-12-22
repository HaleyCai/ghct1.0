package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.StudentMapper;

import java.math.BigInteger;

/**
 * @author caiyq
 */
@Component
public class StudentDao {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 根据学生account查询个人信息
     * @param account
     * @return
     */
    public User getUserByAccount(String account)
    {
        User resultUser=studentMapper.getStudentByAccount(account);
        return resultUser;
    }

    /**
     * 根据学生account激活用户
     * @param account
     * @param password
     * @param email
     * @return
     */
    public boolean activeByAccount(String account,String password,String email)
    {
        int v1,v2,v3;
        v1=studentMapper.setStudentActiveByAccount(account);
        v2=studentMapper.setStudentPasswordByAccount(account,password);
        v3=studentMapper.setStudentEmailByAccount(account,email);
        if(v1==1 && v2==1 && v3==1)
            return true;
        else return false;
    }

    /**
     * 根据学生account修改密码
     * @param account
     * @param password
     * @return
     */
    public boolean setPasswordByAccount(String account,String password)
    {
        int v1=studentMapper.setStudentPasswordByAccount(account,password);
        if(v1==1)
            return true;
        else
            return false;
    }

    /**
     * 根据学生account修改邮箱
     * @param account
     * @param email
     * @return
     */
    public boolean setEmailByAccount(String account,String email)
    {
        int v1=studentMapper.setStudentEmailByAccount(account,email);
        if(v1==1)
            return true;
        else
            return false;
    }

    /**
     * 根据studentId获取学生信息
     * @param studentId
     * @return
     */
    public  User getStudentByStudentId(BigInteger studentId){
        return studentMapper.getStudentByStudentId(studentId);
    }
}
