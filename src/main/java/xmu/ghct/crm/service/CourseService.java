package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.ClassNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.*;

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

    public int creatCourse( List<List<Map>> courseMap) throws ParseException {
        CourseVO courseVO =new CourseVO();
        Map<String,Object> infoMap=courseMap.get(0).get(0);
        courseVO.setCourseName(infoMap.get("courseName").toString());
        courseVO.setIntroduction(infoMap.get("introduction").toString());
        courseVO.setPresentationPercentage(new Double(infoMap.get("presentationPercentage").toString()));
        courseVO.setQuestionPercentage(new Double(infoMap.get("questionPercentage").toString()));
        courseVO.setReportPercentage(new Double(infoMap.get("reportPercentage").toString()));
        courseVO.setTeacherId(new BigInteger(infoMap.get("teacherId").toString()));
        Date end = dateDao.transferToDateTime(infoMap.get("teamEndTime").toString());
        courseVO.setTeamEndTime(end);
        Date start = dateDao.transferToDateTime(infoMap.get("teamStartTime").toString());
        courseVO.setTeamStartTime(start);
        courseVO.setMinMember(new Integer(infoMap.get("minMember").toString()));
        courseVO.setMaxMember(new Integer(infoMap.get("maxMember").toString()));
        courseVO.setFlag(new Boolean(infoMap.get("flag").toString()));
        List<CourseLimitVO> courseLimitVOS=new ArrayList<>();
        List<Map> courseLimitMap=courseMap.get(1);
        int size=courseMap.get(1).size();
        while(size>0){
            CourseLimitVO courseLimitVO=new CourseLimitVO();
            courseLimitVO.setCourseId(new BigInteger(courseLimitMap.get(size-1).get("courseId").toString()));
            courseLimitVO.setCourseName(courseLimitMap.get(size-1).get("courseName").toString());
            courseLimitVO.setMaxMember(new Integer(courseLimitMap.get(size-1).get("maxMember").toString()));
            courseLimitVO.setMinMember(new Integer(courseLimitMap.get(size-1).get("minMember").toString()));
            courseLimitVOS.add(courseLimitVO);
            size--;
        }
        System.out.println(courseLimitVOS);
        courseVO.setCourseLimitVOS(courseLimitVOS);
        List<Map> conflictCourseIdMap=courseMap.get(2);
        int conflictSize=courseMap.get(2).size();
        List<BigInteger> courseIdList=new ArrayList<>();
        while(conflictSize>0){
            BigInteger courseId=new BigInteger(conflictCourseIdMap.get(conflictSize-1).get("courseId").toString());
            courseIdList.add(courseId);
            conflictSize--;
        }
        courseVO.setConflictCourseIdS(courseIdList);
       // courseMap.get("courseLimitVOS")
       // List<CourseLimitVO> courseLimitVOS=new ArrayList<>();
        //courseVO.setCourseLimitVOS(new List<CourseLimitVO>());
        return courseDao.insertCourse(courseVO);
    }

    public List<CourseTeacherVO> teacherGetCourse(BigInteger teacherId) throws ClassNotFoundException {
        List<CourseTeacherVO> courseTeachers=new ArrayList<>();
        //根据teacherId查course
        List<CourseTeacherVO> courses=courseDao.listCourseByTeacherId(teacherId);
        if(courses==null){
            throw new ClassNotFoundException(String.format("not find teacher'courses whose id is %d",teacherId),"404");
        }
        for(CourseTeacherVO item:courses)
        {
            System.out.println(item);
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

    public Course getCourseByCourseId(BigInteger courseId) {
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
//    public  List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
//        List<StudentCourseVO> studentCourseVOS=courseDao.listKlassStudentByStudentId(studentId);
//        for(StudentCourseVO item:studentCourseVOS){
//            Klass klass=klassDao.getKlassByKlassId(item.getKlassId());
//            item.setKlassSerial(klass.getKlassSerial());
//            item.setGrade(klass.getGrade());
//            Course course=courseDao.getCourseByCourseId(item.getCourseId());
//            item.setCourseName(course.getCourseName());
//        }
//        return studentCourseVOS;
//    }

}
