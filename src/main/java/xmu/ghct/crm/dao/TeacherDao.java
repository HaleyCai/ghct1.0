package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.TeacherMapper;

import java.math.BigInteger;
import java.util.List;

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

    /**
     * 管理员创建一个教师
     * @param teacher
     * @return
     */
    public int createTeacher(User teacher) {
        return teacherMapper.createTeacher(teacher);
    }

    /**
     *管理员获得所有教师信息
     * @return
     */
    public List<User> getAllTeacher()
    {
        List<User> resultUser=teacherMapper.getAllTeacher();
        return resultUser;
    }

    /**
     * 管理员根据教师姓名获得教师信息
     * @param teacherName
     * @return
     */
    public User getTeacherByTeacherName(String teacherName)
    {
        User resultUser=teacherMapper.getTeacherByTeacherName(teacherName);
        return resultUser;
    }

    /**
     * 管理员修改某一教师的信息（姓名，账号，邮箱）
     * @return
     */
    public boolean modifyTeacherByTeacherId(BigInteger teacherId,String teacherName,
                                            String teacherAccount,String teacherEmail)
    {
        int v1=teacherMapper.modifyTeacherByTeacherId(teacherId,teacherName,
                teacherAccount,teacherEmail);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    /**
     * 管理员重置某一教师的密码
     * @return
     */
    public boolean resetTeacherPasswordByTeacherId(BigInteger teacherId,String teacherPassword)
    {
        int v1=teacherMapper.resetTeacherPasswordByTeacherId(teacherId,teacherPassword);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    /**
     * 管理员按ID删除某一教师
     * @return
     */
    public boolean deleteTeacherByTeacherId(BigInteger teacherId)
    {
        int v1=teacherMapper.deleteTeacherByTeacherId(teacherId);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }
}