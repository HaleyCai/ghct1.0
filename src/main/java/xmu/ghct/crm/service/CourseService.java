package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.entity.*;

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

    public List<User> getNoTeamStudentByCourseId(BigInteger courseId) {
        return courseDao.getNoTeamStudentByCourseId(courseId);
    }

    public List<Score> listScoreByCourseId(BigInteger courseId){
        return courseDao.listScoreByCourseId(courseId);
    }

    public List<Klass> listKlassByCourseId(BigInteger courseId){
        return courseDao.listKlassByCourseId(courseId);
    }

    public void createKlass( BigInteger courseId,Map<String,Object> klassMap)  {
        Klass klass=new Klass();
        klass.setGrade(new Integer(klassMap.get("grade").toString()));
        klass.setKlassSerial(new Integer(klassMap.get("klassSerial").toString()));
        klass.setKlassTime(klassMap.get("klassTime").toString());
        klass.setKlassLocation(klassMap.get("klassLocation").toString());
        courseDao.createKlass(courseId,klass);
    }

    public void deleteClassByCourseIdAndClassId(BigInteger courseId,BigInteger classId){
          courseDao.deleteClassByCourseIdAndClassId(courseId, classId);
    }

    public List<Share> getShareMessageByCourseId(BigInteger courseId) {
        return courseDao.getShareMessageByCourseId(courseId);
    }

    public  void deleteShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        courseDao.deleteShareByCourseIdAndShareId(courseId, shareId);
    }

    public void launchShareRequest( BigInteger courseId,Map<String,Object> shareMap)  {
        Share share=new Share();
        share.setShareId(new BigInteger(shareMap.get("shareId").toString()));
        share.setShareType(shareMap.get("shareType").toString());
        share.setMainCourseId(new BigInteger(shareMap.get("mainCourseId").toString()));
        share.setSubCourseId(new BigInteger(shareMap.get("subCourseId").toString()));
        share.setSubCourseTeacherId(new BigInteger(shareMap.get("subCourseTeacherId").toString()));
        share.setStatus(new Boolean(shareMap.get("status").toString()));
        courseDao.launchShareRequest(courseId,share);
    }

}
