package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseLimitVO;
import xmu.ghct.crm.VO.CourseStudentVO;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.StudentCourseVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.ClassNotFoundException;
import xmu.ghct.crm.mapper.*;

import java.math.BigInteger;
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

    public List<Course> listCourseByTeacherId(BigInteger teacherId) throws ClassNotFoundException {
        List<Course> courseList = courseMapper.listCourseByTeacherId(teacherId);
        if (courseList == null) {
            throw new ClassNotFoundException("未查找到用户课程数据！");
        }
        else return courseList;
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

    public  List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
        return courseMapper.listKlassStudentByStudentId(studentId);
    }
}