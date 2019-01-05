package xmu.ghct.crm.controller;

import javafx.beans.value.ObservableObjectValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.DateDao;
import xmu.ghct.crm.dao.StrategyDao;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@CrossOrigin
@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    KlassService klassService;

    @Autowired
    UserService userService;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    PresentationService presentationService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    UploadExcelService uploadExcelService;

    @Autowired
    StrategyDao strategyDao;

    @Autowired
    TeamService teamService;

    @Autowired
    DateDao dateDao;

    @Autowired
    ScoreService scoreService;

    @Autowired
    ScoreController scoreController;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * @cyq
     * 教师通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,main(主、从）
     * @return
     */
    @RequestMapping(value="/getCourse/teacher",method = RequestMethod.GET)
    public List<CourseTeacherVO> teacherGetCourse(HttpServletRequest request) throws NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        return courseService.teacherGetCourse(id);
    }

    /**
     * @cyq
     * 学生通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,klassId,klassName(Grade+KlassSerial)
     * @return
     */
    @RequestMapping(value="/getCourse/student",method = RequestMethod.GET)
    public List<CourseStudentVO> studentGetCourse(HttpServletRequest request) throws NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        return courseService.studentGetCourse(id);
    }


    /**
     * 创建课程
     * @return
     * @throws ParseException
     */
    @RequestMapping(value="/course/creatCourse",method = RequestMethod.POST)
    public boolean creatCourse(HttpServletRequest request,@RequestBody NewCourseVO newCourseVO) throws ParseException, SQLException {
        System.out.println("Controller course"+newCourseVO);
        int flag= courseService.creatCourse(request,newCourseVO);
        if(flag>0)return true;
        else return false;
    }

    /**
     * 根据课程ID查找课程
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    public NewCourseVO getCourseByCourseId(@PathVariable("courseId")String courseId) throws NotFoundException {
        return courseService.getCourseByCourseId(new BigInteger(courseId));
    }


    /**
     * 根据课程ID删除课程
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    public boolean deleteCourseByCourseId(@PathVariable("courseId")String courseId) throws NotFoundException {
        int flag=courseService.deleteCourseByCourseId(new BigInteger(courseId));
        if(flag>0)return true;
        else return false;
    }

    /**
     * 获得课程下的所有轮次
     * @param courseId
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value="/course/{courseId}/round",method = RequestMethod.GET)
    @ResponseBody
    public  List<Round> listRoundByCourseId(@PathVariable("courseId") String courseId) throws NotFoundException {
        List<Round> roundList=courseService.listRoundByCourseId(new BigInteger(courseId));
        if(roundList==null){
         throw new NotFoundException("未找到该课程下的讨论课轮次数据!");
        }
        else return roundList;
    }


    /**
     * @author hzm
     * pc端获取某课程轮次信息及轮次下所有讨论课基础信息
     * @param courseId
     * @return
     */
    @GetMapping("/course/{courseId}/pc")
    public List<List<Map>> listRoundAndSeminarByCourseId(@PathVariable("courseId") String courseId) throws NotFoundException {
        List<Round> roundList=courseService.listRoundByCourseId(new BigInteger(courseId));
        List<List<Map>> map=new ArrayList<>();
        for(Round round:roundList){
            List<Map> mapList=new ArrayList<>();
            Map<String,Object> roundMap=new HashMap<>();
            roundMap.put("roundId",round.getRoundId());
            roundMap.put("roundSerial",round.getRoundSerial());
            mapList.add(roundMap);
            List<Seminar> seminarList=seminarService.listSeminarByRoundId(round.getRoundId());
            for(Seminar item:seminarList){
                Map<String,Object> oneMap=new HashMap<>();
                oneMap.put("seminarId",item.getSeminarId());
                oneMap.put("seminarName",item.getSeminarName());
                oneMap.put("seminarSerial",item.getSeminarSerial());
                mapList.add(oneMap);
            }
            map.add(mapList);
        }
        return map;
    }

    /**
     * 获取讨论课信息及讨论课所属全部班级
     * @param seminarId
     * @return
     */
    @GetMapping("/seminar/{seminarId}/attendance/pc")
    public List<Map> listKlassInfoBySeminarId(@PathVariable("seminarId")String seminarId) throws NotFoundException{
        Seminar seminar=seminarService.getSeminarBySeminarId(new BigInteger(seminarId));
        List<Klass> klassList=klassService.listKlassBySeminarId(new BigInteger(seminarId));
        List<Map> map=new ArrayList<>();
        Map<String, Object> oneMap=new HashMap<>();
        oneMap.put("seminarId",seminar.getSeminarId());
        oneMap.put("seminarSerial",seminar.getSeminarSerial());
        oneMap.put("seminarName",seminar.getSeminarName());
        oneMap.put("introduction",seminar.getIntroduction());
        oneMap.put("enrollStartTime",seminar.getEnrollStartTime());
        oneMap.put("enrollEndTime",seminar.getEnrollEndTime());
        map.add(oneMap);
        for(Klass klass:klassList){
            Map<String,Object> klassMap=new HashMap<>();
            klassMap.put("klassSerial",klass.getKlassSerial());
            klassMap.put("klassId",klass.getKlassId());
            map.add(klassMap);
        }
        return map;
    }

    /**
     * 获取班级讨论课报名信息   //教师
     * @param seminarId
     * @param klassId
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/seminar/{seminarId}/{klassId}/klassSeminar/pc")
    public List<Map> listAttendanceStatusByKlassIdAndSeminar(@PathVariable("seminarId")String seminarId,@PathVariable("klassId")String klassId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        SeminarVO klassSeminar=seminarService.getKlassSeminarByKlassIdAndSeminarId(new BigInteger(klassId),new BigInteger(seminarId));
        List<Map> map=new ArrayList<>();
        List<Attendance> attendanceList=presentationService.listAttendanceByKlassSeminarId(klassSeminar.getKlassSeminarId());
        int maxTeam=klassSeminar.getMaxTeam();
        int account=0;
        for(Attendance attendance:attendanceList){
            account++;
            System.out.println(account);
            Map<String,Object> oneMap=new HashMap<>();
           if(account!=attendance.getTeamOrder()){
               System.out.println(attendance.getTeamOrder());
               oneMap.put("attendanceStatus",false);
               map.add(oneMap);
               continue;
           }
           else oneMap.put("attendanceStatus",true);
            BigInteger teamId=attendance.getTeamId();
            Team team=teamService.getTeamInfoByTeamId(teamId);
            Klass klass=klassService.getKlassByKlassId(team.getKlassId());
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
            oneMap.put("reportName",pptName);
            oneMap.put("reportUrl",reportUrl);
            map.add(oneMap);
        }
        if(account<maxTeam){
            account++;
            Map<String,Object> oneMap=new HashMap<>();
            oneMap.put("attendanceStatus",false);
            map.add(oneMap);
        }
        return map;
    }


    /**
     * 获取班级讨论课报名信息   //学生
     * @param seminarId
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/seminar/{seminarId}/klass/klassSeminar/pc")
    public List<Map> listAttendanceStatusBySeminarId(HttpServletRequest request,
                                                             @PathVariable("seminarId")String seminarId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        Seminar seminar=seminarService.getSeminarBySeminarId(new BigInteger(seminarId));
        BigInteger klassId=klassService.getKlassIdByCourseIdAndStudentId(seminar.getCourseId(),id);
        SeminarVO klassSeminar=seminarService.getKlassSeminarByKlassIdAndSeminarId(klassId,new BigInteger(seminarId));
        List<Map> map=new ArrayList<>();
        List<Attendance> attendanceList=presentationService.listAttendanceByKlassSeminarId(klassSeminar.getKlassSeminarId());
        int maxTeam=klassSeminar.getMaxTeam();
        int account=0;
        for(Attendance attendance:attendanceList){
            account++;
            System.out.println(account);
            Map<String,Object> oneMap=new HashMap<>();
            if(account!=attendance.getTeamOrder()){
                System.out.println(attendance.getTeamOrder());
                oneMap.put("attendanceStatus",false);
                map.add(oneMap);
                continue;
            }
            else oneMap.put("attendanceStatus",true);
            BigInteger teamId=attendance.getTeamId();
            Team team=teamService.getTeamInfoByTeamId(teamId);
            Klass klass=klassService.getKlassByKlassId(team.getKlassId());
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
            oneMap.put("reportName",pptName);
            oneMap.put("reportUrl",reportUrl);
            map.add(oneMap);
        }
        if(account<maxTeam){
            account++;
            Map<String,Object> oneMap=new HashMap<>();
            oneMap.put("attendanceStatus",false);
            map.add(oneMap);
        }
        return map;
    }


    /**
     * pc端获取某课程下各个班级的学生名单提交情况
     * @param courseId
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/course/{courseId}/klass/pc")
    public List<Map> listKlassAndStudentListByCourseId(@PathVariable("courseId")String courseId) throws NotFoundException{
        List<Klass> klassList=klassService.listKlassByCourseId(new BigInteger(courseId));
        List<Map> map=new ArrayList<>();
        for(Klass klass:klassList){
            Map<String,Object> oneMap=new HashMap<>();
            int studentNumber=klassService.getStudentNumber(klass.getKlassId());
            if(studentNumber>0) oneMap.put("submitStatus",true);
            else oneMap.put("submitStatus",false);
            oneMap.put("klassSerial",klass.getKlassSerial());
            oneMap.put("klassId",klass.getKlassId());
            oneMap.put("courseId",klass.getCourseId());
            oneMap.put("grade",klass.getGrade());
            map.add(oneMap);
        }
        return map;
    }


    /**
     * pc端获取轮次下讨论课成绩
     * @param roundId
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/round/{roundId}/seminarScore/pc")
    public List<Map> listKlassSeminarScoreByRoundId(@PathVariable("roundId")String roundId) throws NotFoundException{
        List<ScoreVO> scoreVOList=scoreService.listRoundScoreByRoundId(new BigInteger(roundId));
        List<Map> map=new ArrayList<>();
        for(ScoreVO scoreVO:scoreVOList){
            List<Score> scoreList=scoreService.listKlassSeminarScoreByRoundIdAndTeamId(new BigInteger(roundId),scoreVO.getTeamId());
            for(Score score:scoreList){
                Map<String,Object> oneMap=new HashMap<>();
                oneMap.put("roundTotalScore",scoreVO.getTotalScore());
                oneMap.put("seminarName",score.getSeminarName());
                oneMap.put("presentationScore",score.getPresentationScore());
                oneMap.put("questionScore",score.getQuestionScore());
                oneMap.put("reportScore",score.getReportScore());
                oneMap.put("totalScore",score.getTotalScore());
                oneMap.put("klassSerial",scoreVO.getKlassSerial());
                oneMap.put("teamSerial",scoreVO.getTeamSerial());
                oneMap.put("teamId",scoreVO.getTeamId());
                map.add(oneMap);
            }
        }
        return map;
    }

    /**
     * pc端重置学生名单（删除全部班级学生）
     * @param klassId
     * @return
     * @throws NotFoundException
     */
    @DeleteMapping("/klass/{klassId}/resetStudent/pc")
    public boolean resetStudentList(@PathVariable("klassId") String klassId)throws NotFoundException{
        return courseService.resetStudentList(new BigInteger(klassId));
    }



    /**
     * @cyq
     * 根据roundId获取轮次信息
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.GET)
    public RoundVO getRoundByRoundId(@PathVariable("roundId") String roundId,
                                     @RequestParam String courseId) throws NotFoundException {
        RoundVO roundVO=courseService.getRoundByRoundId(
                new BigInteger(courseId),
                new BigInteger(roundId));
        return roundVO;
    }

    /**
     * @cyq
     * 根据roundId修改轮次信息（修改轮次的评分方式, 修改本轮各个班级允许的报名次数）
     * @return
     */
    @RequestMapping(value = "/round/modifyRoundInfo",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@RequestBody Map<String,Object> inMap) throws NotFoundException {
        //修改轮次的评分方式
        RoundVO roundVO=new RoundVO();
        roundVO.setCourseId(new BigInteger(inMap.get("courseId").toString()));
        roundVO.setRoundId(new BigInteger(inMap.get("roundId").toString()));
        roundVO.setRoundSerial(Integer.valueOf(inMap.get("roundSerial").toString()));
        roundVO.setPresentationScoreMethod(inMap.get("presentationScoreMethod").toString());
        roundVO.setQuestionScoreMethod(inMap.get("reportScoreMethod").toString());
        roundVO.setReportScoreMethod(inMap.get("questionScoreMethod").toString());
        return courseService.modifyRoundByRoundId(roundVO);
    }

    @RequestMapping(value = "/round/{roundId}/modifyRoundEnroll",method = RequestMethod.PUT)
    public boolean modifyRoundEnrollByRoundId(@PathVariable String roundId,
                                               @RequestBody RoundEnrollListVO list)
    {
        List<RoundEnrollVO> roundEnrollVOS=list.getRoundEnrollList();
//        for(Map<String,Object> map:inList.getList())
//        {
//            RoundEnrollVO enrollVO=new RoundEnrollVO();
//            enrollVO.setKlassId(new BigInteger(map.get("klassId").toString()));
//            enrollVO.setKlassSerial((int)map.get("klassSerial"));
//            enrollVO.setGrade((int)map.get("grade"));
//            enrollVO.setEnroll((int)map.get("enroll"));
//            roundEnrollVOS.add(enrollVO);
//        }
        return courseService.modifyRoundEnrollByRoundId(new BigInteger(roundId),roundEnrollVOS);
    }


    /**
     * @author hzm
     * 获取该教师所有正在进行的班级讨论课id
     * @return
     */
    @GetMapping("/{teacherId}/course/beingPresent")
    public BigInteger isBeingPresentSeminar(HttpServletRequest request) throws NotFoundException {
        BigInteger teacherId=jwtTokenUtil.getIDFromRequest(request);
        return courseService.isBeingPresentSeminar(teacherId);
    }



//    @PostMapping("/TEST")
//    public void test(@RequestBody ArrayList<Map> listMap){
//        Map<String,ArrayList<BigInteger>> map=listMap.get(1);
//        BigInteger bigIntegers=new BigInteger(map.get("studentId").get(0).toString());
//        System.out.println(bigIntegers);
//    }


    /**
     * 创建课程时获得所有课程信息，以设置冲突课程
     * @return
     */
    @RequestMapping(value = "/course/getAllCourse",method = RequestMethod.GET)
    public List<Map> getAllCourse() throws NotFoundException
    {
        return courseService.getAllCourse();
    }
}