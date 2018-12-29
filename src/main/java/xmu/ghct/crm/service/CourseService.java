package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.ArrayList;
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

    @Autowired
    KlassDao klassDao;

    @Autowired
    SeminarDao seminarDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    TeamService teamService;

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

    public List<CourseTeacherVO> teacherGetCourse(BigInteger teacherId)
    {
        List<CourseTeacherVO> courseTeachers=new ArrayList<>();
        //根据teacherId查course
        List<Course> courses=courseDao.listCourseByTeacherId(teacherId);
        for(Course item:courses)
        {
            CourseTeacherVO courseTeacherVO=new CourseTeacherVO();
            courseTeacherVO.setCourseId(item.getCourseId());
            courseTeacherVO.setCourseName(item.getCourseName());
            courseTeachers.add(courseTeacherVO);
        }
        return courseTeachers;
    }

    public List<CourseStudentVO> studentGetCourse(BigInteger studentId)
    {
        return courseDao.listCourseByStudentId(studentId);
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

    /**
     * @author hzm
     * 根据klassId获取courseId
     * @param klassId
     * @return
     */
    public BigInteger getCourseIdByKlassId(BigInteger klassId){
        return courseDao.getCourseIdByKlassId(klassId);
    }


    /**
     * @author hzm
     * 获取教师所有课程中正在进行的班级讨论课ID
     * @param teacherId
     * @return
     */
    public BigInteger isBeingPresentSeminar(BigInteger teacherId){
        List<BigInteger> courseIdList=courseDao.listCourseIdByTeacherId(teacherId);
        System.out.println(courseIdList);
        for(BigInteger courseId:courseIdList){
            List<BigInteger> klassIdList=courseDao.listKlassIdByCourseId(courseId);
            System.out.println(klassIdList);
            for(BigInteger klassId:klassIdList){
                List<SeminarVO> seminarVOList=seminarDao.listKlassSeminarIdByKlassId(klassId);
                System.out.println(seminarVOList);
                for(SeminarVO seminarVO:seminarVOList){
                    if(seminarVO.getStatus()>0) return seminarVO.getKlassSeminarId();
                }
            }
        }
        return new BigInteger("0");
    }


    /**
     * @author hzm
     * 获取学生所有班级及其课程ID
     * @param studentId
     * @return
     */
    public  List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
        List<StudentCourseVO> studentCourseVOS=courseDao.listKlassStudentByStudentId(studentId);
        for(StudentCourseVO item:studentCourseVOS){
            Klass klass=klassDao.getKlassByKlassId(item.getKlassId());
            item.setKlassSerial(klass.getKlassSerial());
            item.setGrade(klass.getGrade());
            CourseVO courseVO=courseDao.getCourseByCourseId(item.getCourseId());
            item.setCourseName(courseVO.getCourseName());
        }
        return studentCourseVOS;
    }

}
