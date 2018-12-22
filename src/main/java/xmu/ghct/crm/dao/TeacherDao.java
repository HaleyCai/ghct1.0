package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.TeacherMapper;

/**
 * @author caiyq
 */
@Component
public class TeacherDao {
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 根据教师account查询个人信息
     * @param account
     * @return
     */
    public User getUserByAccount(String account)
    {
        User resultUser=teacherMapper.getTeacherByAccount(account);
        return resultUser;
    }

    /**
     * 根据教师account激活用户
     * @param account
     * @param password
     * @return
     */
    public boolean activeByAccount(String account,String password)
    {
        int v1,v2;
        v1=teacherMapper.setTeacherActiveByAccount(account);
        v2=teacherMapper.setTeacherPasswordByAccount(account,password);
        //教师激活时不用输入邮箱
        if(v1==1 && v2==1)
            return true;
        else return false;
    }

    /**
     * 根据教师account修改密码
     * @param account
     * @param password
     * @return
     */
    public boolean setPasswordByAccount(String account,String password)
    {
        int v1=teacherMapper.setTeacherPasswordByAccount(account,password);
        if(v1==1)
            return true;
        else
            return false;
    }

    /**
     * 根据教师account修改邮箱
     * @param account
     * @param email
     * @return
     */
    public boolean setEmailByAccount(String account,String email)
    {
        int v1=teacherMapper.setTeacherEmailByAccount(account,email);
        if(v1==1)
            return true;
        else
            return false;
    }
}
