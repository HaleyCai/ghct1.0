package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.mapper.CourseMapper;

@Component
public class CourseDao {

    @Autowired
    CourseMapper courseMapper;

    public void insertCourse(Course course){
        courseMapper.insertCourse(course);
    }


}
