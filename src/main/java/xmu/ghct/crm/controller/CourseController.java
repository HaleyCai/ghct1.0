package xmu.ghct.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.StrategyDao;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.ClassNotFoundException;
import xmu.ghct.crm.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

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

    /**
     * @cyq
     * 教师通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,main(主、从）
     * @param inMap
     * @return
     */
    @RequestMapping(value="/getCourse/teacher",method = RequestMethod.GET)
    public List<CourseTeacherVO> teacherGetCourse(@RequestBody Map<String,Object> inMap) throws ClassNotFoundException {
        return courseService.teacherGetCourse(new BigInteger(inMap.get("teacherId").toString()));
    }

    /**
     * @cyq
     * 学生通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,klassId,klassName(Grade+KlassSerial)
     * @param inMap
     * @return
     */
    @RequestMapping(value="getCourse/student",method = RequestMethod.GET)
    public List<CourseStudentVO> studentGetCourse(@RequestBody Map<String,Object> inMap)
    {
        return courseService.studentGetCourse(new BigInteger(inMap.get("studentId").toString()));
    }


    @RequestMapping(value="/course/creatCourse",method = RequestMethod.POST)
    public boolean creatCourse(@RequestBody List<List<Map>> courseMap) throws ParseException {
        int flag= courseService.creatCourse(courseMap);
        if(flag>0)return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    public Course getCourseByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    public boolean deleteCourseByCourseId(@PathVariable("courseId")BigInteger courseId) {
        int flag=courseService.deleteCourseByCourseId(courseId);
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
    public  List<Round> listRoundByCourseId(@PathVariable("courseId") BigInteger courseId) throws ClassNotFoundException {
        List<Round> roundList=courseService.listRoundByCourseId(courseId);
        if(roundList==null){
         throw new ClassNotFoundException("未找到该课程下的讨论课轮次数据!");
        }
        else return roundList;
    }

    /**
     * @cyq
     * 根据roundId获取轮次信息
     * @cyq
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.GET)
    public RoundVO getRoundByRoundId(@PathVariable("roundId") BigInteger roundId,
                                     @RequestBody Map<String,Object> inMap)
    {
        return courseService.getRoundByRoundId(
                new BigInteger(inMap.get("courseId").toString()),
                roundId);
    }

    /**
     * @cyq
     * 根据roundId修改轮次信息（修改轮次的评分方式, 修改本轮各个班级允许的报名次数）
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@PathVariable("roundId") BigInteger roundId,
                                        @RequestBody Map<String,Object> inMap) throws IllegalAccessException
    {
        //修改轮次的评分方式
        RoundVO roundVO=new RoundVO();
        roundVO.setCourseId(new BigInteger(inMap.get("courseId").toString()));
        roundVO.setRoundId(new BigInteger(inMap.get("roundId").toString()));
        roundVO.setRoundSerial(Integer.valueOf(inMap.get("roundSerial").toString()));
        roundVO.setPresentationScoreMethod(inMap.get("presentationScoreMethod").toString());
        roundVO.setQuestionScoreMethod(inMap.get("reportScoreMethod").toString());
        roundVO.setReportScoreMethod(inMap.get("questionScoreMethod").toString());
        //将map中的object转为map
        Map<String,String> enroll=new TreeMap<>();
//        Class<?> clazz = inMap.get("enrollNum").getClass();
//        System.out.println(clazz);
//        for (Field field : clazz.getDeclaredFields()) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            System.out.println("fieldName "+ fieldName);
//            Integer value = Integer.valueOf(field.get(inMap.get("enrollNum")).toString());
//            System.out.println("value "+ value);
//            enroll.put(fieldName, value);
//        }
        System.out.println("roundVO "+roundVO);
        //enroll.put(inMap.get(6).toString(),inMap.get(6).toString());
        System.out.println("map:   "+enroll);
        //roundVO.setEnrollNum(enroll);
        //修改本轮各个班级允许的报名次数
        return false;//courseService.modifyRoundByRoundId(roundVO);
    }

    /**
     * @author hzm
     * 获取该教师所有正在进行的班级讨论课id
     * @param teacherId
     * @return
     */
    @GetMapping("/{teacherId}/course/beingPresent")
    public BigInteger isBeingPresentSeminar(@PathVariable("teacherId")BigInteger teacherId){
           return courseService.isBeingPresentSeminar(teacherId);
    }

//    @GetMapping("/{studentId}/course")
//    public  List<StudentCourseVO> listKlassStudentByStudentId(@PathVariable("studentId") BigInteger studentId){
//        return courseService.listKlassStudentByStudentId(studentId);
//    }


    @PostMapping("/{klassId}/uploadStudentNameList")
    public String add(@PathVariable("klassId")BigInteger klassId,@RequestParam("file")MultipartFile file){
        //Map<Integer, Map<Integer,Object>> map = uploadExcelService.addCustomerInfo(file);
        uploadExcelService.addCustomerInfo(klassId,file);
        //System.out.println(map);
        return "success";
    }

    @PostMapping("/TEST")
    public void test(@RequestParam BigInteger teamId){
        System.out.println(teamService.judgeIllegal(teamId));
    }


    /**
     * 创建课程时获得所有课程信息，以设置冲突课程
     * @return
     */
    @RequestMapping(value = "/course/setConflictCourse",method = RequestMethod.GET)
    public List<Map> getAllCourse()
    {
        return courseService.getAllCourse();
    }
}