package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseLimitVO;
import xmu.ghct.crm.VO.CourseStudentVO;
import xmu.ghct.crm.VO.CourseTeacherVO;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.StudentCourseVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.ClassNotFoundException;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CourseDao {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    KlassMapper klassMapper;

    @Autowired
    ShareMapper shareMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StrategyMapper strategyMapper;



    public int insertCourse(CourseVO courseVO) throws SQLException {

        BigInteger isCourseId=courseMapper.getCourseIdByCourseNameAndTeacherId(courseVO.getCourseName(),courseVO.getTeacherId());
        if(isCourseId!=null)
        {
            throw new SQLException("该课程已存在");
        }
        int flag_1=courseMapper.insertCourse(courseVO);
        int flag_2=strategyMapper.insertMemberLimit(courseVO);
        int strategySerial=1;
        strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,courseVO.getMemberLimitId(),"MemberLimitStrategy");
        strategySerial++;
        List<CourseLimitVO> courseLimitVOS=courseVO.getCourseLimitVOS();
        if(courseVO.isFlag()==true){
            BigInteger id=strategyMapper.selectMaxIdFromTeamAndStrategy().add(new BigInteger("1"));
            System.out.println("strategy_id:"+id);
            for(CourseLimitVO item:courseLimitVOS){
                strategyMapper.insertCourseMemberLimit(item);
                strategyMapper.insertAndStrategy(id,"CourseMemberLimitStrategy",item.getCourseLimitId());
            }
            strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,id,"TeamAndStrategy");
            strategySerial++;
        }
        else{
            BigInteger id=strategyMapper.selectMaxIdFromTeamOrStrategy().add(new BigInteger("1"));
            for(CourseLimitVO item:courseLimitVOS){
                strategyMapper.insertCourseMemberLimit(item);
                strategyMapper.insertOrStrategy(id,"CourseMemberLimitStrategy",item.getCourseLimitId());
            }
            strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,id,"TeamOrStrategy");
            strategySerial++;
        }
        BigInteger conflictCourseId=strategyMapper.selectMaxIdFromConflictCourseStrategy().add(new BigInteger("1"));
        for(BigInteger item:courseVO.getConflictCourseIdS()){
            strategyMapper.insertIntoConflictCourseStrategy(conflictCourseId,item);
        }
        strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,conflictCourseId,"ConflictCourseStrategy");
        return (flag_1&flag_2);
    }

    public List<CourseTeacherVO> listCourseByTeacherId(BigInteger teacherId) throws NotFoundException {
        //根据teacherId查course
        List<CourseTeacherVO> courseTeachers = new ArrayList<>();
        List<Course> courses = courseMapper.listCourseByTeacherId(teacherId);
        if(courses==null||courses.isEmpty()){
            throw new NotFoundException("未找到该教师的课程信息！");
        }
        for (Course item : courses) {
            CourseTeacherVO courseTeacherVO = new CourseTeacherVO();
            courseTeacherVO.setCourseId(item.getCourseId());
            courseTeacherVO.setCourseName(item.getCourseName());
            courseTeachers.add(courseTeacherVO);
        }
        return courseTeachers;
    }

    public List<CourseStudentVO> listCourseByStudentId(BigInteger studentId) throws NotFoundException {
        List<CourseStudentVO> courseStudentVOList=courseMapper.listCourseByStudentId(studentId);
        if(courseStudentVOList==null||courseStudentVOList.isEmpty()){
            throw new NotFoundException("未找到该学生用户的课程信息！");
        }
        for(CourseStudentVO item:courseStudentVOList)
        {
            //根据CourseId查courseName
            Course course=courseMapper.getCourseByCourseId(item.getCourseId());
            item.setCourseName(course.getCourseName());
            //根据klassId查grade和klassSerial
            Klass klass=klassMapper.getKlassByKlassId(item.getKlassId());
            item.setGrade(klass.getGrade());
            item.setKlassSerial(klass.getKlassSerial());
        }
        return courseStudentVOList;
    }

    public Course getCourseByCourseId(BigInteger courseId) throws NotFoundException {
        Course course = courseMapper.getCourseByCourseId(courseId);
        if (course == null) {
            throw new NotFoundException("未查找该课程");
        }
        return course;
    }

    public int deleteCourseByCourseId(BigInteger courseId) throws NotFoundException {
        if(courseMapper.getCourseByCourseId(courseId)==null)
        {
            throw new NotFoundException("未查找该课程");
        }
        return courseMapper.deleteCourseByCourseId(courseId);
    }

    public BigInteger getCourseIdByTeamId(BigInteger teamId) throws NotFoundException {
        BigInteger id=courseMapper.getCourseIdByTeamId(teamId);
        if(id==null)
        {
            throw new NotFoundException("未查找该课程");
        }
        return id;
    }

    public BigInteger getCourseIdByKlassId(BigInteger klassId) throws NotFoundException {
        BigInteger id=courseMapper.getCourseIdByKlassId(klassId);
        if(id==null)
        {
            throw new NotFoundException("未查找该课程");
        }
        return id;
    }

    public List<BigInteger> listCourseIdByTeacherId(BigInteger teacherId) throws NotFoundException {
        List<BigInteger> list = courseMapper.listCourseIdByTeacherId(teacherId);
        if (list == null && list.isEmpty()) {
            throw new NotFoundException("未找到该教师的课程列表");
        }
        return list;
    }

    public List<BigInteger> listKlassIdByCourseId(BigInteger courseId) throws NotFoundException
    {
        List<BigInteger> list=courseMapper.listKlassIdByCourseId(courseId);
        if(list==null&&list.isEmpty())
        {
            throw new NotFoundException("未找到该课程下的班级列表");
        }
        return list;
    }

    public List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
        return courseMapper.listKlassStudentByStudentId(studentId);
    }

    public String getTeacherNameByCourseId(BigInteger courseId) throws NotFoundException
    {
        BigInteger teacherId=courseMapper.getCourseByCourseId(courseId).getTeacherId();
        if(teacherId==null)
        {
            throw new NotFoundException("未找到该教师");
        }
        return teacherMapper.getTeacherById(teacherId).getName();
    }

    public List<CourseVO> getAllCourse() throws NotFoundException
    {
        List<CourseVO> courseVO=courseMapper.getAllCourse();
        if(courseVO==null&&courseVO.isEmpty())
        {
            throw new NotFoundException("尚未有课程");
        }
        return courseVO;
    }

    public List<Course> getCourseByTeacherId(BigInteger teacherId)
    {
        return courseMapper.getCourseByTeacherId(teacherId);
    }
}