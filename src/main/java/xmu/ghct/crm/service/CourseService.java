package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Team;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    public void creatCourse( Map<String,Object> courseMap) throws ParseException {
        Course course=new Course();
        course.setCourseId(new BigInteger(courseMap.get("id").toString()));
        course.setCourseName(courseMap.get("courseName").toString());
        course.setIntroduction(courseMap.get("introduction").toString());
        course.setPresentationPercentage(new Double(courseMap.get("presentationPercentage").toString()));
        course.setQuestionPercentage(new Double(courseMap.get("questionPercentage").toString()));
        course.setReportPercentage(new Double(courseMap.get("reportPercentage").toString()));
        course.setTeacherId(new BigInteger(courseMap.get("teacherId").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
        course.setTeamEndTime(formatter.parse(courseMap.get("teamEndTime").toString()));
        course.setTeamStartTime(formatter.parse(courseMap.get("teamStartTime").toString()));
        courseDao.insertCourse(course);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId) {
        return courseDao.listCourseByTeacherId(teacherId);
    }

    public List<Course> getCourseByCourseId(BigInteger courseId) {
        return courseDao.getCourseByCourseId(courseId);
    }

    public void deleteCourseByCourseId(BigInteger courseId){
        courseDao.deleteCourseByCourseId(courseId);
    }

    public List<Team> getTeamMessageByCourseId(BigInteger courseId) {
        return courseDao.getTeamMessageByCourseId(courseId);
    }
}
