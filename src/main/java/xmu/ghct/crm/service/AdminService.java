package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.exception.NotFoundException;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.math.BigInteger;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class AdminService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;

    private static Pattern pattern = Pattern.compile("[0-9]+");
    /**
     * 管理员创建一个教师
     *
     * @return
     */
    public int createTeacher(Map<String, Object> teacherMap) throws SQLException {
        User teacher = new User();
        teacher.setName(teacherMap.get("name").toString());
        teacher.setAccount(teacherMap.get("account").toString());
        teacher.setEmail(teacherMap.get("email").toString());
        return teacherDao.createTeacher(teacher);
    }

    /**
     *管理员获得所有教师/学生信息
     * @return
     */
    public List<User> getAllUser(int type) throws NotFoundException
    {
        if(type==1) {
            return teacherDao.getAllTeacher();
        } else {
            return studentDao.getAllStudent();
        }
    }

    /**
     * 管理员根据用户姓名或账号获得用户信息
     * @return
     */
    public User getUser(String str,int type) throws NotFoundException
    {
        User user;
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            if(type==1) {
                user=teacherDao.getTeacherByTeacherName(str);
            } else {
                user = studentDao.getStudentByStudentName(str);
            }
        }
        else {
            if(type==1) {
                user=teacherDao.getTeacherByAccount(str);
            } else {
                user=studentDao.getStudentByAccount(str);
            }
        }
        return user;
    }

    /**
     * 管理员修改某一用户的信息（姓名，账号，邮箱）
     * @return
     */
    public boolean modifyUserByUserId(BigInteger userId,String newUserName,
                                      String newUserAccount,String newUserEmail,int type) throws SQLException
    {
        boolean success;
        if(type==1) {
            success=teacherDao.modifyTeacherByTeacherId(userId,newUserName,newUserAccount,newUserEmail);
        } else {
            success=studentDao.modifyStudentByStudentId(userId,newUserName,newUserAccount,newUserEmail);
        }
        return success;
    }

    /**
     * 管理员重置某一用户的密码
     * @return
     */
    public boolean resetUserPasswordByUserId(BigInteger userId,int type)
    {
        boolean success;
        if(type==1) {
            success=teacherDao.resetTeacherPasswordByTeacherId(userId);
        } else {
            success=studentDao.resetStudentPasswordByStudentId(userId);
        }
        return success;
    }

    /**
     * 管理员按ID删除某一用户
     * @return
     */
    public boolean deleteUserByUserId(BigInteger userId,int type) throws NotFoundException
    {
        boolean success;
        if(type==1) {
            success=teacherDao.deleteTeacherByTeacherId(userId);
        } else {
            success=studentDao.deleteStudentByStudentId(userId);
        }
        return success;
    }
}


