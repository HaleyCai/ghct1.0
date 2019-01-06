package xmu.ghct.crm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.vo.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gfj
 */
@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    PresentationDao presentationDao;

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
    StudentDao studentDao;

    @Autowired
    SeminarService seminarService;

    @Autowired
    UserService userService;

    @Autowired
    ScoreDao scoreDao;

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


    public boolean resetStudentList(BigInteger klassId)throws NotFoundException{
        List<BigInteger> studentIdList=studentDao.listStudentByKlassId(klassId);
        List<BigInteger> teamIdList=teamDao.listTeamIdByKlassId(klassId);
        int flag=studentDao.deleteKlassStudentByKlassId(klassId);
        for(BigInteger studentId:studentIdList){
            studentDao.deleteStudentByStudentId(studentId);
        }
        for(BigInteger teamId:teamIdList){
            teamDao.deleteTeam(teamId);
        }
        if(flag>0) {
            return true;
        } else {
            return false;
        }
    }

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


    public List<Map> listAttendanceStatusByKlassIdAndSeminar(BigInteger seminarId,BigInteger klassId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        SeminarVO klassSeminar=seminarService.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);
        List<Map> map=new ArrayList<>();
        List<Attendance> attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminar.getKlassSeminarId());
        int maxTeam=klassSeminar.getMaxTeam();
        int account=0;
        for(int i=0;i<attendanceList.size();){
            account++;
            System.out.println(account);
            Attendance attendance=attendanceList.get(i);
            Map<String,Object> oneMap=new HashMap<>();
            if(attendance.getTeamOrder()>account){
                System.out.println(attendance.getTeamOrder());
                oneMap.put("attendanceStatus",false);
                map.add(oneMap);
                continue;
            }
            else {
                oneMap.put("attendanceStatus",true);
                oneMap.put("attendanceId",attendance.getAttendanceId());
                i++;
            }
            BigInteger teamId=attendance.getTeamId();
            Team team=teamService.getTeamInfoByTeamId(teamId);
            Klass klass=klassDao.getKlassByKlassId(team.getKlassId());
            User user=userService.getInformation(team.getLeaderId(),"student");
            oneMap.put("klassId",team.getKlassId());
            oneMap.put("klassSerial",klass.getKlassSerial());
            oneMap.put("status",klassSeminar.getStatus());
            oneMap.put("reportDDL",klassSeminar.getReportDDL());
            oneMap.put("teamSerial",team.getTeamSerial());
            oneMap.put("teamOrder",attendance.getTeamOrder());
            oneMap.put("leaderName",user.getName());
            String pptName=attendance.getPptName();
            String pptUrl=attendance.getPptUrl();
            if(pptName==null||pptName.length()<=0){
                pptName="未提交";
                pptUrl=null;
            }

            oneMap.put("pptName",pptName);
            oneMap.put("pptUrl",pptUrl);
            String reportName=attendance.getReportName();
            String reportUrl=attendance.getReportUrl();
            if(reportName==null||reportName.length()<=0){
                reportName="未提交";
                reportUrl=null;
            }
            oneMap.put("reportName",reportName);
            oneMap.put("reportUrl",reportUrl);
            map.add(oneMap);
        }
        while(account<maxTeam){
            account++;
            Map<String,Object> oneMap=new HashMap<>();
            oneMap.put("attendanceStatus",false);
            map.add(oneMap);
        }
        return map;
    }

    public NewCourseVO getCourseByCourseId(BigInteger courseId) throws NotFoundException {
        NewCourseVO newCourseVO=new NewCourseVO();
        Course course= courseDao.getCourseByCourseId(courseId);
        BeanUtils.copyProperties(course,newCourseVO);
        newCourseVO.setTeamStartTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(course.getTeamStartTime()));
        newCourseVO.setTeamEndTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(course.getTeamEndTime()));
        List<TeamStrategyVO> teamStrategyVOList=strategyDao.listTeamStrategyByCourseId(courseId);
        List<List<Course>> courseList=new ArrayList<>();
        for(TeamStrategyVO teamStrategyVO:teamStrategyVOList){
            if("ConflictCourseStrategy".equals(teamStrategyVO.getStrategyName())){
                BigInteger strategyId=teamStrategyVO.getStrategyId();
                List<BigInteger> conflictCourseId=strategyDao.listConflictCourseId(strategyId);
                List<Course> courses=new ArrayList<>();
                for(BigInteger courseIdItem:conflictCourseId){
                    Course newCourse=new Course();
                    Course course1=courseDao.getCourseByCourseId(courseIdItem);
                    User user=userService.getInformation(course1.getTeacherId(),"teacher");
                    newCourse.setTeacherName(user.getName());
                    newCourse.setCourseId(courseIdItem);
                    newCourse.setCourseName(course1.getCourseName());
                    courses.add(course1);
                }
                courseList.add(courses);
            }
            else if("TeamAndStrategy".equals(teamStrategyVO.getStrategyName())) {
                List<AndOrOrStrategyVO> andOrOrStrategyVOS = strategyDao.selectAndStrategy(teamStrategyVO.getStrategyId());
                for (AndOrOrStrategyVO andOrOrStrategyVO : andOrOrStrategyVOS) {
                    if ("MemberLimitStrategy".equals(andOrOrStrategyVO.getStrategyName())) {
                        CourseVO courseVO1 = strategyDao.getTeamMemberLimit(andOrOrStrategyVO.getStrategyId());
                        newCourseVO.setMinMember(String.valueOf(courseVO1.getMinMember()));
                        newCourseVO.setMaxMember(String.valueOf(courseVO1.getMaxMember()));
                    } else if ("TeamOrStrategy".equals(andOrOrStrategyVO.getStrategyName())) {
                        List<AndOrOrStrategyVO> andOrOrStrategyVOList = strategyDao.selectOrStrategy(andOrOrStrategyVO.getStrategyId());
                        List<CourseLimitVO> courseLimitVOS = new ArrayList<>();
                        for (AndOrOrStrategyVO orStrategy : andOrOrStrategyVOList) {
                            CourseLimitVO courseLimitVO = strategyDao.getCourseLimitByStrategyId(orStrategy.getStrategyId());
                            Course course1 = courseDao.getCourseByCourseId(courseLimitVO.getCourseId());
                            courseLimitVO.setCourseName(course1.getCourseName());
                            courseLimitVOS.add(courseLimitVO);
                        }
                        newCourseVO.setCourseLimitVOS(courseLimitVOS);
                    }else if("CourseMemberStrategy".equals(andOrOrStrategyVO.getStrategyName())){
                        List<BigInteger> strategyIdList = strategyDao.listStrategyIdByStrategyId(teamStrategyVO.getStrategyId());
                        List<CourseLimitVO> courseLimitVOS = new ArrayList<>();
                        for (BigInteger strategyId : strategyIdList) {
                            CourseLimitVO courseLimitVO = strategyDao.getCourseLimitByStrategyId(strategyId);
                            Course course1=courseDao.getCourseByCourseId(courseLimitVO.getCourseId());
                            courseLimitVO.setCourseName(course1.getCourseName());
                            courseLimitVOS.add(courseLimitVO);
                        }
                        newCourseVO.setCourseLimitVOS(courseLimitVOS);
                    }

                }
            }

        }
        newCourseVO.setConflictCourseIdS(courseList);
      return newCourseVO;
    }

    public int deleteCourseByCourseId(BigInteger courseId) throws NotFoundException {
        return courseDao.deleteCourseByCourseId(courseId);
    }

    public List<Round> listRoundByCourseId(BigInteger courseId){
        return roundDao.listRoundByCourseId(courseId);
    }

    public List<Round> listRoundAndScoreByCourseId(BigInteger courseId,BigInteger teamId){
        List<Round> roundList=roundDao.listRoundByCourseId(courseId);
        for(Round round:roundList)
        {
            Double roundTotal=scoreDao.getTeamRoundScoreByRoundIdAndTeamId(round.getRoundId(),teamId).getTotalScore();
            round.setTotalScore(roundTotal);
        }
        return roundList;
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
                    if(seminarVO.getStatus()>0) {
                        return seminarVO.getKlassSeminarId();
                    }
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
