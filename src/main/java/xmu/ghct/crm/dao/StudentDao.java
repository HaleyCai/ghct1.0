package xmu.ghct.crm.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.StudentMapper;

import java.math.BigInteger;
import java.util.List;

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
     *管理员获得所有学生信息
     * @return
     */
    public List<User> getAllStudent()
    {
        List<User> resultUser=studentMapper.getAllStudent();
        return resultUser;
    }

    /**
     * 管理员根据学生姓名获得学生信息
     * @param studentName
     * @return
     */
    public User getStudentByStudentName(String studentName)
    {
        User resultUser=studentMapper.getStudentByStudentName(studentName);
        return resultUser;
    }

    /**
     * 管理员修改某一学生的信息（姓名，账号，邮箱）
     * @return
     */
    public boolean modifyStudentByStudentId(BigInteger studentId,String studentName,
                                            String studentAccount,String studentEmail)
    {
        int v1=studentMapper.modifyStudentByStudentId(studentId,studentName,
                                                        studentAccount,studentEmail);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    /**
     * 管理员重置某一学生的密码
     * @return
     */
    public boolean resetStudentPasswordByStudentId(BigInteger studentId,String studentPassword)
    {
        int v1=studentMapper.resetStudentPasswordByStudentId(studentId,studentPassword);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    /**
     * 管理员按ID删除某一学生
     * @return
     */
    public boolean deleteStudentByStudentId(BigInteger studentId)
    {
        int v1=studentMapper.deleteStudentByStudentId(studentId);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }
}
