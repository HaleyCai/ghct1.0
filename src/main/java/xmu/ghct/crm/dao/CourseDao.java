package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseVO;
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

    public CourseVO getCourseByCourseId(BigInteger courseId) {
        Course course = courseMapper.getCourseByCourseId(courseId);
        CourseVO courseVO =teamMapper.getTeamMemberLimit();
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

}