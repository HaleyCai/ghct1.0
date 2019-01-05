package xmu.ghct.crm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.math.BigInteger;
import java.util.*;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    TeacherDao teacherDao;

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

    @Autowired
    StrategyDao strategyDao;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

  /*  public int creatCourse( courseVO) throws ParseException, SQLException {
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
    */

    public int creatCourse(HttpServletRequest request,NewCourseVO newCourseVO) throws SQLException, ParseException {
        BigInteger teacherId=jwtTokenUtil.getIDFromRequest(request);
        CourseVO courseVO=new CourseVO();
        System.out.println("course=="+newCourseVO);
        System.out.println("dateEndTime==="+newCourseVO.getTeamEndTime());
        Date end = dateDao.transferToDateTime(newCourseVO.getTeamEndTime());
        courseVO.setTeamEndTime(end);
        Date start = dateDao.transferToDateTime(newCourseVO.getTeamStartTime());
        courseVO.setTeamStartTime(start);
        courseVO.setCourseName(newCourseVO.getCourseName());
        courseVO.setIntroduction(newCourseVO.getIntroduction());
        courseVO.setPresentationPercentage(newCourseVO.getPresentationPercentage());
        courseVO.setQuestionPercentage(newCourseVO.getQuestionPercentage());
        courseVO.setReportPercentage(newCourseVO.getReportPercentage());
        courseVO.setTeacherId(teacherId);
        courseVO.setMinMember(new Integer(newCourseVO.getMinMember()));
        courseVO.setMaxMember(new Integer(newCourseVO.getMaxMember()));
        courseVO.setFlag(newCourseVO.isFlag());
        courseVO.setCourseLimitVOS(newCourseVO.getCourseLimitVOS());
        List<List<BigInteger>> courseIdList=new ArrayList<>();
        if(newCourseVO.getConflictCourseIdS()!=null&&newCourseVO.getConflictCourseIdS().size()>0){
            for(List<Course> courseS:newCourseVO.getConflictCourseIdS()){
                if(courseS!=null&&courseS.size()>0){
                    List<BigInteger> courseIdS=new ArrayList<>();
                    for(Course course:courseS){
                        courseIdS.add(course.getCourseId());
                    }
                    courseIdList.add(courseIdS);
                }
            }
        }
        courseVO.setConflictCourseIdS(courseIdList);
        return courseDao.insertCourse(courseVO);
    }

    public List<CourseTeacherVO> teacherGetCourse(BigInteger teacherId) throws NotFoundException {
        List<CourseTeacherVO> courseTeachers=new ArrayList<>();
        //根据teacherId查course
        List<CourseTeacherVO> courses=courseDao.listCourseByTeacherId(teacherId);
        System.out.println(courses);
        for(CourseTeacherVO item:courses)
        {
            System.out.println("Find!");
            System.out.println(item);
            CourseTeacherVO courseTeacherVO=new CourseTeacherVO();
            courseTeacherVO.setCourseId(item.getCourseId());
            courseTeacherVO.setCourseName(item.getCourseName());
            courseTeachers.add(courseTeacherVO);
        }
        return courseTeachers;
    }

    public List<CourseStudentVO> studentGetCourse(BigInteger studentId) throws NotFoundException {
        return courseDao.listCourseByStudentId(studentId);
    }

    public CourseVO getCourseByCourseId(BigInteger courseId) throws NotFoundException {
        CourseVO courseVO=new CourseVO();
        Course course= courseDao.getCourseByCourseId(courseId);
        BeanUtils.copyProperties(course,courseVO);
        List<TeamStrategyVO> teamStrategyVOList=strategyDao.listTeamStrategyByCourseId(courseId);
        List<List<BigInteger>> conflictCourseIdList=new ArrayList<>();
        for(TeamStrategyVO teamStrategyVO:teamStrategyVOList){
            if(teamStrategyVO.getStrategyName().equals("ConflictCourseStrategy")){
                BigInteger strategyId=teamStrategyVO.getStrategyId();
                List<BigInteger> conflictCourseId=strategyDao.listConflictCourseId(strategyId);
                conflictCourseIdList.add(conflictCourseId);
            }
            else if(teamStrategyVO.getStrategyName().equals("TeamAndStrategy")){
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectAndStrategy(teamStrategyVO.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                    if(andOrOrStrategyVO.getStrategyName().equals("MemberLimitStrategy")){
                        CourseVO courseVO1=strategyDao.getTeamMemberLimit(andOrOrStrategyVO.getStrategyId());
                        courseVO.setMinMember(courseVO1.getMinMember());
                        courseVO.setMaxMember(courseVO1.getMaxMember());
                        courseVO.setMemberLimitId(andOrOrStrategyVO.getStrategyId());
                    } else if(andOrOrStrategyVO.getStrategyName().equals("TeamOrStrategy")){
                        List<AndOrOrStrategyVO> andOrOrStrategyVOList=strategyDao.selectOrStrategy(andOrOrStrategyVO.getStrategyId());
                        List<CourseLimitVO> courseLimitVOS=new ArrayList<>();
                        for(AndOrOrStrategyVO orStrategy:andOrOrStrategyVOList){
                            CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(orStrategy.getStrategyId());
                            courseLimitVOS.add(courseLimitVO);
                        }
                        courseVO.setCourseLimitVOS(courseLimitVOS);
                    }
                }
                List<BigInteger> strategyIdList=strategyDao.listStrategyIdByStrategyId(teamStrategyVO.getStrategyId());
                List<CourseLimitVO> courseLimitVOS=new ArrayList<>();
                for(BigInteger strategyId:strategyIdList){
                    CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(strategyId);
                    courseLimitVOS.add(courseLimitVO);
                }
                courseVO.setCourseLimitVOS(courseLimitVOS);
            }
        }
        courseVO.setConflictCourseIdS(conflictCourseIdList);
      return courseVO;
    }

    public int deleteCourseByCourseId(BigInteger courseId) throws NotFoundException {
        return courseDao.deleteCourseByCourseId(courseId);
    }

    public List<Round> listRoundByCourseId(BigInteger courseId) throws NotFoundException {
        return roundDao.listRoundByCourseId(courseId);
    }

    /**
     * @cyq
     * 根据courseId和roundId获得轮次信息
     * @param courseId
     * @param roundId
     * @return
     */
    public RoundVO getRoundByRoundId(BigInteger courseId,BigInteger roundId) throws NotFoundException {
        return roundDao.getRoundByRoundId(courseId,roundId);
    }

    /**
     * @cyq
     * 根据roundId修改轮次的信息（成绩评定方式和班级报名次数）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundByRoundId(RoundVO roundVO) throws NotFoundException {
        return roundDao.modifyRoundByRoundId(roundVO);
    }

    public boolean modifyRoundEnrollByRoundId(BigInteger roundId,List<RoundEnrollVO> list)
    {
        return roundDao.modifyRoundEnrollByRoundId(roundId,list);
    }

    /**
     * @author hzm
     * 根据klassId获取courseId
     * @param klassId
     * @return
     */
    public BigInteger getCourseIdByKlassId(BigInteger klassId) throws NotFoundException {
        return courseDao.getCourseIdByKlassId(klassId);
    }


    /**
     * @author hzm
     * 获取教师所有课程中正在进行的班级讨论课ID
     * @param teacherId
     * @return
     */
    public BigInteger isBeingPresentSeminar(BigInteger teacherId) throws NotFoundException {
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

    /**
     * 创建课程时获得所有课程信息，以设置冲突课程
     * @return
     */
     public List<Map> getAllCourse() throws NotFoundException
     {
         List<CourseVO> courseList=courseDao.getAllCourse();
         List<Map> map=new ArrayList<>();
         for(CourseVO item:courseList)
         {
             Map<String,Object> oneMap=new HashMap<>();
             oneMap.put("CourseId",item.getCourseId());
             oneMap.put("CourseName",item.getCourseName());
             oneMap.put("TeacherId",item.getTeacherId());
             oneMap.put("TeacherName",teacherDao.getTeacherNameByTeacherId(item.getTeacherId()));
             map.add(oneMap);
         }
         return map;

     }

    public BigInteger getCourseIdByRoundId(BigInteger roundId){
        return roundDao.getCourseIdByRoundId(roundId);
    }

}
