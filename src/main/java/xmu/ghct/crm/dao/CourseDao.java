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
import java.util.ArrayList;
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


    public int insertCourse(CourseVO courseVO) {

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
            System.out.println("notFind!");
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

    public List<CourseStudentVO> listCourseByStudentId(BigInteger studentId)
    {
        List<CourseStudentVO> courseStudentVOList=courseMapper.listCourseByStudentId(studentId);
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

    public Course getCourseByCourseId(BigInteger courseId) {
        Course course = courseMapper.getCourseByCourseId(courseId);
        if (course == null) {
            //throw new CourseNotFindException();
        }
        return course;
    }

    public int deleteCourseByCourseId(BigInteger courseId) {
        return courseMapper.deleteCourseByCourseId(courseId);
    }

    public BigInteger getCourseIdByTeamId(BigInteger teamId){
        return courseMapper.getCourseIdByTeamId(teamId);
    }

    public BigInteger getCourseIdByKlassId(BigInteger klassId){return courseMapper.getCourseIdByKlassId(klassId);}

    public List<BigInteger> listCourseIdByTeacherId(BigInteger teacherId){
        return courseMapper.listCourseIdByTeacherId(teacherId);
    }

    public List<BigInteger> listKlassIdByCourseId(BigInteger courseId){
        return courseMapper.listKlassIdByCourseId(courseId);
    }

    public List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
        return courseMapper.listKlassStudentByStudentId(studentId);
    }

    public String getTeacherNameByCourseId(BigInteger courseId)
    {
        BigInteger teacherId=courseMapper.getCourseByCourseId(courseId).getTeacherId();
        return teacherMapper.getTeacherById(teacherId).getName();
    }

    public List<CourseVO> getAllCourse()
    {
        return courseMapper.getAllCourse();
    }

    public List<Course> getCourseByTeacherId(BigInteger teacherId)
    {
        return courseMapper.getCourseByTeacherId(teacherId);
    }
}