package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.CreatCourseVO;
import xmu.ghct.crm.VO.RoundVO;
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
        CreatCourseVO creatCourseVO=new CreatCourseVO();
        creatCourseVO.setCourseId(new BigInteger(courseMap.get("id").toString()));
        creatCourseVO.setCourseName(courseMap.get("courseName").toString());
        creatCourseVO.setIntroduction(courseMap.get("introduction").toString());
        creatCourseVO.setPresentationPercentage(new Double(courseMap.get("presentationPercentage").toString()));
        creatCourseVO.setQuestionPercentage(new Double(courseMap.get("questionPercentage").toString()));
        creatCourseVO.setReportPercentage(new Double(courseMap.get("reportPercentage").toString()));
        creatCourseVO.setTeacherId(new BigInteger(courseMap.get("teacherId").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date end = formatter.parse(courseMap.get("teamEndTime").toString()+" 00:00:00");
        creatCourseVO.setTeamEndTime(end);
        Date start = formatter.parse(courseMap.get("teamStartTime").toString()+" 00:00:00");
        creatCourseVO.setTeamStartTime(start);
        creatCourseVO.setMinMember(new Integer(courseMap.get("minMember").toString()));
        creatCourseVO.setMaxMember(new Integer(courseMap.get("maxMember").toString()));
        return courseDao.insertCourse(creatCourseVO);
    }

    public List<Course> listCourseByTeacherId(Map<String,Object> teacherIdMap) {
        BigInteger teacherId=new BigInteger(teacherIdMap.get("teacherId").toString());
        return courseDao.listCourseByTeacherId(teacherId);
    }

    public CreatCourseVO getCourseByCourseId(BigInteger courseId) {
        return courseDao.getCourseByCourseId(courseId);
    }

    public int deleteCourseByCourseId(BigInteger courseId){
        return courseDao.deleteCourseByCourseId(courseId);
    }

    /**
     * 根据roundId获取轮次的信息
     * @param roundId
     * @return
     */
    public RoundVO getRoundByRoundId(BigInteger roundId)
    {
        return roundDao.getRoundByRoundId(roundId);
    }

    /**
     * 根据roundId修改轮次的信息（成绩评定方式）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundMethodByRoundId(RoundVO roundVO)
    {
        return roundDao.modifyRoundMethodByRoundId(roundVO);
    }

    /**
     * 根据课程id，顺序，创建新的轮次
     * @param round
     * @return
     */
    public BigInteger createRound(Round round)
    {
        return roundDao.createRound(round);
    }

}
