package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlCall;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.StudentMapper;

import java.math.BigInteger;
import java.sql.SQLException;
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
    public User getStudentByAccount(String account)
    {
        User resultUser=studentMapper.getStudentByAccount(account);

        return resultUser;
    }

    /**
     * 根据学生id查询个人信息
     * @return
     */
    public User getStudentById(BigInteger studentId) throws NotFoundException
    {
        User resultUser=studentMapper.getStudentByStudentId(studentId);
        if(resultUser==null)
        {
            throw new NotFoundException("未找到该学生");
        }
        return resultUser;
    }

    /**
     * 根据学生id激活用户
     * @param id
     * @param password
     * @param email
     * @return
     */
    public boolean activeById(BigInteger id,String password,String email)
    {
        int v1,v2,v3;
        v1=studentMapper.setStudentActiveById(id);
        v2=studentMapper.setStudentPasswordById(id,password);
        v3=studentMapper.setStudentEmailById(id,email);
        if(v1==1 && v2==1 && v3==1)
            return true;
        else return false;
    }

    /**
     * 根据学生id修改密码
     * @param id
     * @param password
     * @return
     */
    public boolean setPasswordById(BigInteger id,String password) throws SQLException
    {
        if(studentMapper.getStudentByStudentId(id).getPassword().equals(password))
        {
            throw new SQLException("密码未改变");
        }
        int v1=studentMapper.setStudentPasswordById(id,password);
        if(v1==1)
            return true;
        else
            return false;
    }

    /**
     * 根据学生id修改邮箱
     * @param id
     * @param email
     * @return
     */
    public boolean setEmailById(BigInteger id,String email) throws SQLException
    {
        if(studentMapper.getStudentByStudentId(id).getEmail().equals(email))
        {
            throw new SQLException("邮箱未改变");
        }
        int v1=studentMapper.setStudentEmailById(id,email);
        if(v1==1)
            return true;
        else
            return false;
    }

    /**
     *管理员获得所有学生信息
     * @return
     */
    public List<User> getAllStudent() throws NotFoundException
    {
        List<User> resultUser=studentMapper.getAllStudent();
        if(resultUser==null&&resultUser.isEmpty())
        {
            throw new NotFoundException("未找到该学生");
        }
        return resultUser;
    }

    /**
     * 管理员根据学生姓名获得学生信息
     * @param studentName
     * @return
     */
    public User getStudentByStudentName(String studentName) throws NotFoundException
    {
        User resultUser=studentMapper.getStudentByStudentName(studentName);
        if(resultUser==null)
        {
            throw new NotFoundException("未找到该学生");
        }
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
    public boolean resetStudentPasswordByStudentId(BigInteger studentId)
    {
        int v1=studentMapper.resetStudentPasswordByStudentId(studentId);
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
    public boolean deleteStudentByStudentId(BigInteger studentId) throws NotFoundException
    {
        int v1=studentMapper.deleteStudentByStudentId(studentId);
        if(v1<=0){
            throw new NotFoundException("该学生已被删除");
        }
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    /**
     * @author hzm
     * 插入学生用户
     * @param user
     * @return
     */
    public int insertStudent(User user) throws SQLException
    {
        return studentMapper.insertStudent(user);
    }

    public int insertKlassStudent(BigInteger studentId,BigInteger klassId,BigInteger courseId) throws SQLException
    {
        return studentMapper.insertKlassStudent(studentId,klassId,courseId);
    }

    public List<BigInteger> getAllStudentIdByCourseId(BigInteger courseId)
    {
        return studentMapper.getAllStudentIdByCourseId(courseId);
    }

    /**
     * 获取班级下所有学生ID
     * @param klassId
     * @return
     */
    public List<BigInteger> listStudentByKlassId(BigInteger klassId){
        return studentMapper.listStudentByKlassId(klassId);
    }


    /**
     * 删除班级下所有学生关联
     * @param klassId
     * @return
     */
    public int deleteKlassStudentByKlassId(BigInteger klassId){
        return studentMapper.deleteKlassStudentByKlassId(klassId);
    }
}