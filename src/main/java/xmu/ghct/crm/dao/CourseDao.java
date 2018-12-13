package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.mapper.CourseMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class CourseDao {

    @Autowired
    CourseMapper courseMapper;

    public void insertCourse(Course course){
        courseMapper.insertCourse(course);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId){
        List<Course> courseList=courseMapper.listCourseByTeacherId(teacherId);
        if(courseList==null){
           //throw new CourseNotFindException();
        }
        return courseList;
    }

    public List<Course> listCourseByCourseId(BigInteger courseId){
        List<Course> courseList=courseMapper.listCourseByCourseId(courseId);
        if(courseList==null){
            //throw new CourseNotFindException();
        }
        return courseList;
    }

    public void deleteCourseByCourseId(BigInteger courseId){
        courseMapper.deleteCourseByCourseId(courseId);
    }


}
