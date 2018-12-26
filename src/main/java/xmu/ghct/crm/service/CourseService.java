package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.DateDao;
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

    @Autowired
    DateDao dateDao;

    public int creatCourse( Map<String,Object> courseMap) throws ParseException {
        CourseVO courseVO =new CourseVO();
        courseVO.setCourseName(courseMap.get("courseName").toString());
        courseVO.setIntroduction(courseMap.get("introduction").toString());
        courseVO.setPresentationPercentage(new Double(courseMap.get("presentationPercentage").toString()));
        courseVO.setQuestionPercentage(new Double(courseMap.get("questionPercentage").toString()));
        courseVO.setReportPercentage(new Double(courseMap.get("reportPercentage").toString()));
        courseVO.setTeacherId(new BigInteger(courseMap.get("teacherId").toString()));
        Date end = dateDao.transferToDateTime(courseMap.get("teamEndTime").toString());
        courseVO.setTeamEndTime(end);
        Date start = dateDao.transferToDateTime(courseMap.get("teamStartTime").toString());
        courseVO.setTeamStartTime(start);
        courseVO.setMinMember(new Integer(courseMap.get("minMember").toString()));
        courseVO.setMaxMember(new Integer(courseMap.get("maxMember").toString()));
        return courseDao.insertCourse(courseVO);
    }

    public List<Course> listCourseByTeacherId(Map<String,Object> teacherIdMap) {
        BigInteger teacherId=new BigInteger(teacherIdMap.get("teacherId").toString());
        List<Course> courseList=courseDao.listCourseByTeacherId(teacherId);
        return courseList;
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

    /**
     * @cyq
     * 根据courseId和roundId获得轮次信息
     * @param courseId
     * @param roundId
     * @return
     */
    public RoundVO getRoundByRoundId(BigInteger courseId,BigInteger roundId)
    {
        return roundDao.getRoundByRoundId(courseId,roundId);
    }

    /**
     * @cyq
     * 根据roundId修改轮次的信息（成绩评定方式和班级报名次数）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundByRoundId(RoundVO roundVO)
    {
        return roundDao.modifyRoundByRoundId(roundVO);
    }

}
