package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.User;

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

    /**
     * 管理员创建一个教师
     *
     * @return
     */
    public int createTeacher(Map<String, Object> teacherMap) {
        User teacher = new User();
        teacher.setName(teacherMap.get("name").toString());
        teacher.setAccount(teacherMap.get("account").toString());
        teacher.setEmail(teacherMap.get("email").toString());
        teacher.setPassword(teacherMap.get("password").toString());
        return teacherDao.createTeacher(teacher);
    }

    /**
     *管理员获得所有教师/学生信息
     * @return
     */
    public List<User> getAllUser(String role)
    {
        if(role=="teacher")
            return teacherDao.getAllTeacher();
        else
            return studentDao.getAllStudent();
    }

    /**
     * 管理员根据用户姓名或账号获得用户信息
     * @return
     */
    public User getUser(String str,String role)
    {
        User user;

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            if(role=="teacher")
                user=teacherDao.getTeacherByTeacherName(str);
            else
                user=studentDao.getStudentByStudentName(str);
        }
        else {
            if(role=="teacher")
                user=teacherDao.getTeacherByAccount(str);
            else
                user=studentDao.getStudentByAccount(str);
        }
        return user;
    }

    /**
     * 管理员修改某一用户的信息（姓名，账号，邮箱）
     * @return
     */
    public boolean modifyUserByUserId(BigInteger userId,String newUserName,
                                      String newUserAccount,String newUserEmail,String role)
    {
        boolean success;
        if(role=="teacher")
            success=teacherDao.modifyTeacherByTeacherId(userId,newUserName,newUserAccount,newUserEmail);
        else
            success=studentDao.modifyStudentByStudentId(userId,newUserName,newUserAccount,newUserEmail);
        return success;
    }

    /**
     * 管理员重置某一用户的密码
     * @return
     */
    public boolean resetUserPasswordByUserId(BigInteger userId,String role)
    {
        boolean success;
        if(role=="teacher")
            success=teacherDao.resetTeacherPasswordByTeacherId(userId);
        else
            success=studentDao.resetStudentPasswordByStudentId(userId);
        return success;
    }

    /**
     * 管理员按ID删除某一用户
     * @return
     */
    public boolean deleteUserByUserId(BigInteger userId,String role)
    {
        boolean success;
        if(role=="teacher")
            success=teacherDao.deleteTeacherByTeacherId(userId);
        else
            success=studentDao.deleteStudentByStudentId(userId);
        return success;
    }
}


