package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CreatCourseVO;
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


    public int insertCourse(CreatCourseVO creatCourseVO) {
        return courseMapper.insertCourse(creatCourseVO);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId) {
        List<Course> courseList = courseMapper.listCourseByTeacherId(teacherId);
        if (courseList == null) {
            //throw new CourseNotFindException();
        }
        return courseList;
    }

    public CreatCourseVO getCourseByCourseId(BigInteger courseId) {
        Course course = courseMapper.getCourseByCourseId(courseId);
        CreatCourseVO creatCourseVO=teamMapper.getTeamMemberLimit();
        creatCourseVO.setIntroduction(course.getIntroduction());
        creatCourseVO.setPresentationPercentage(course.getPresentationPercentage());
        creatCourseVO.setQuestionPercentage(course.getQuestionPercentage());
        creatCourseVO.setReportPercentage(course.getReportPercentage());
        creatCourseVO.setTeamStartTime(course.getTeamStartTime());
        creatCourseVO.setTeamEndTime(course.getTeamEndTime());

        if (creatCourseVO == null) {
            //throw new CourseNotFindException();
        }
        return creatCourseVO;
    }

    public int deleteCourseByCourseId(BigInteger courseId) {
        return courseMapper.deleteCourseByCourseId(courseId);
    }

}