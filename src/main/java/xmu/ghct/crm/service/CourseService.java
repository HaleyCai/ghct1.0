package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.entity.Course;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    public void creatCourse(Course course){
        courseDao.insertCourse(course);
    }
}
