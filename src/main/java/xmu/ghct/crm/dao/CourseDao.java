package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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


    public int insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId) {
        List<Course> courseList = courseMapper.listCourseByTeacherId(teacherId);
        if (courseList == null) {
            //throw new CourseNotFindException();
        }
        return courseList;
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

}