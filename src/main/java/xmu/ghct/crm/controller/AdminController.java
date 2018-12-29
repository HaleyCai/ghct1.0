package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.service.AdminService;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 管理员创建一个教师
     *
     * @return
     */
    @RequestMapping(value="/teacher",method = RequestMethod.POST)
    public boolean creatTeacher(@RequestBody Map<String,Object> inMap){
        int flag= adminService.createTeacher(inMap);
        if(flag>0)return true;
        else return false;
    }

    /**
     *管理员获得所有教师信息
     * @return
     */
    @RequestMapping(value="/teacher",method = RequestMethod.GET)
    public List<User> getAllTeacher(@RequestBody Map<String,Object> inMap){
        return adminService.getAllUser((int)inMap.get("type"));
    }

    /**
     *管理员获得所有学生信息
     * @return
     */
    @RequestMapping(value="/student",method = RequestMethod.GET)
    public List<User> getAllStudent(@RequestBody Map<String,Object> inMap){
        return adminService.getAllUser((int)inMap.get("type"));
    }

    /**
     * 管理员根据教师姓名或账号获得用户信息
     * @return
     */
    @RequestMapping(value="/teacher/searchteacher",method = RequestMethod.GET)
    public User getTeacher(@RequestBody Map<String,Object> inMap){
        return adminService.getUser(
                (String)inMap.get("str"),
                (int)inMap.get("type"));
    }


    /**
     * 管理员根据学生姓名或账号获得用户信息
     * @return
     */
    @RequestMapping(value="/student/searchstudent",method = RequestMethod.GET)
    public User getStudent(@RequestBody Map<String,Object> inMap){
        return adminService.getUser(
                (String)inMap.get("str"),
                (int)inMap.get("type"));
    }


    /**
     * 管理员修改某一教师的信息（姓名，账号，邮箱）
     * @return
     */
    @RequestMapping(value="/teacher/{teacherId}/information",method = RequestMethod.PUT)
    public boolean modifyTeacherByTeacherId(@PathVariable("teacherId")BigInteger teacherId,
                                            @RequestBody Map<String,Object> inMap){
        return adminService.modifyUserByUserId(
                teacherId,
                (String)inMap.get("newTeacherName"),
                (String)inMap.get("newTeacherAccount"),
                (String)inMap.get("newTeacherEmail"),
                (int)inMap.get("type"));
    }

    /**
     * 管理员修改某一学生的信息（姓名，账号，邮箱）
     * @return
     */
    @RequestMapping(value="/student/{studentId}/information",method = RequestMethod.PUT)
    public boolean modifyStudentByStudentId(@PathVariable("studentId")BigInteger studentId,
                                            @RequestBody Map<String,Object> inMap){
        return adminService.modifyUserByUserId(
                studentId,
                (String)inMap.get("newStudentName"),
                (String)inMap.get("newStudentAccount"),
                (String)inMap.get("newStudentEmail"),
                (int)inMap.get("type"));
    }

    /**
     * 管理员重置某一教师的密码
     * @return
     */
    @RequestMapping(value="/teacher/{teacherId}/password",method = RequestMethod.PUT)
    public boolean resetTeacherPasswordByTeacherId(@PathVariable("teacherId")BigInteger teacherId,
                                                   @RequestBody Map<String,Object> inMap){
        return adminService.resetUserPasswordByUserId(
                teacherId,
                (int)inMap.get("type"));
    }

    /**
     * 管理员重置某一学生的密码
     * @return
     */
    @RequestMapping(value="/student/{studentId}/password",method = RequestMethod.PUT)
    public boolean resetStudentPasswordByStudentId(@PathVariable("studentId")BigInteger studentId,
                                                   @RequestBody Map<String,Object> inMap){
        return adminService.resetUserPasswordByUserId(
                studentId,
                (int)inMap.get("type"));
    }

    /**
     * 管理员按ID删除某一教师
     * @return
     */
    @RequestMapping(value="/teacher/{teacherId}",method = RequestMethod.DELETE)
    public boolean deleteTeacherByTeacherId(@PathVariable("teacherId")BigInteger teacherId,
                                            @RequestBody Map<String,Object> inMap){
        return adminService.deleteUserByUserId(
                teacherId,
                (int)inMap.get("type"));
    }

    /**
     * 管理员按ID删除某一学生
     * @return
     */
    @RequestMapping(value="/student/{studentId}",method = RequestMethod.DELETE)
    public boolean deleteStudentByStudentId(@PathVariable("studentId")BigInteger studentId,
                                            @RequestBody Map<String,Object> inMap){
        return adminService.deleteUserByUserId(
                studentId,
                (int)inMap.get("type"));
    }

}