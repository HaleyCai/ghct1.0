package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.RoundDao;
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

    @Autowired
    RoundDao roundDao;

    public int creatCourse( Map<String,Object> courseMap) throws ParseException {
        CourseVO courseVO =new CourseVO();
        courseVO.setCourseId(new BigInteger(courseMap.get("id").toString()));
        courseVO.setCourseName(courseMap.get("courseName").toString());
        courseVO.setIntroduction(courseMap.get("introduction").toString());
        courseVO.setPresentationPercentage(new Double(courseMap.get("presentationPercentage").toString()));
        courseVO.setQuestionPercentage(new Double(courseMap.get("questionPercentage").toString()));
        courseVO.setReportPercentage(new Double(courseMap.get("reportPercentage").toString()));
        courseVO.setTeacherId(new BigInteger(courseMap.get("teacherId").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date end = formatter.parse(courseMap.get("teamEndTime").toString()+" 00:00:00");
        courseVO.setTeamEndTime(end);
        Date start = formatter.parse(courseMap.get("teamStartTime").toString()+" 00:00:00");
        courseVO.setTeamStartTime(start);
        courseVO.setMinMember(new Integer(courseMap.get("minMember").toString()));
        courseVO.setMaxMember(new Integer(courseMap.get("maxMember").toString()));
        return courseDao.insertCourse(courseVO);
    }

    public List<Course> listCourseByTeacherId(Map<String,Object> teacherIdMap) {
        BigInteger teacherId=new BigInteger(teacherIdMap.get("teacherId").toString());
        return courseDao.listCourseByTeacherId(teacherId);
    }

    public CourseVO getCourseByCourseId(BigInteger courseId) {
        return courseDao.getCourseByCourseId(courseId);
    }

    public int deleteCourseByCourseId(BigInteger courseId){
        return courseDao.deleteCourseByCourseId(courseId);
    }

    public List<Round> listRoundByCourseId(BigInteger courseId){
        return roundDao.listRoundByCourseId(courseId);
    }



}
