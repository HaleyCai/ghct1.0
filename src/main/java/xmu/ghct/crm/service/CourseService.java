package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    public int creatCourse( Map<String,Object> courseMap) throws ParseException {
        Course course=new Course();
        course.setCourseId(new BigInteger(courseMap.get("id").toString()));
        course.setCourseName(courseMap.get("courseName").toString());
        course.setIntroduction(courseMap.get("introduction").toString());
        course.setPresentationPercentage(new Double(courseMap.get("presentationPercentage").toString()));
        course.setQuestionPercentage(new Double(courseMap.get("questionPercentage").toString()));
        course.setReportPercentage(new Double(courseMap.get("reportPercentage").toString()));
        course.setTeacherId(new BigInteger(courseMap.get("teacherId").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date end = formatter.parse(courseMap.get("teamEndTime").toString()+" 00:00:00");
        System.out.println(end);
        course.setTeamEndTime(end);
       Date start = formatter.parse(courseMap.get("teamStartTime").toString()+" 00:00:00");
        course.setTeamStartTime(start);
        return courseDao.insertCourse(course);
    }

    public List<Course> listCourseByTeacherId(Map<String,Object> teacherIdMap) {
        BigInteger teacherId=new BigInteger(teacherIdMap.get("teacherId").toString());
        return courseDao.listCourseByTeacherId(teacherId);
    }

    public Course getCourseByCourseId(BigInteger courseId) {
        return courseDao.getCourseByCourseId(courseId);
    }

    public int deleteCourseByCourseId(BigInteger courseId){
        return courseDao.deleteCourseByCourseId(courseId);
    }




}
