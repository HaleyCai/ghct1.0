package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseStudentVO;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.StudentCourseVO;
import xmu.ghct.crm.entity.*;
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


    public int insertCourse(CourseVO courseVO) {

        int flag_1=courseMapper.insertCourse(courseVO);
        int flag_2=teamMapper.insertTeamMemberLimit(courseVO);
        return (flag_1&flag_2);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId) {
        List<Course> courseList = courseMapper.listCourseByTeacherId(teacherId);
        if (courseList == null) {
            //throw new CourseNotFindException();
        }
        return courseList;
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

    public CourseVO getCourseByCourseId(BigInteger courseId) {
        Course course = courseMapper.getCourseByCourseId(courseId);
        CourseVO courseVO =teamMapper.getTeamMemberLimit();
        courseVO.setCourseName(course.getCourseName());
        courseVO.setIntroduction(course.getIntroduction());
        courseVO.setPresentationPercentage(course.getPresentationPercentage());
        courseVO.setQuestionPercentage(course.getQuestionPercentage());
        courseVO.setReportPercentage(course.getReportPercentage());
        courseVO.setTeamStartTime(course.getTeamStartTime());
        courseVO.setTeamEndTime(course.getTeamEndTime());

        if (courseVO == null) {
            //throw new CourseNotFindException();
        }
        return courseVO;
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