package xmu.ghct.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.RoundNotFindException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    /**
     * @cyq
     * 教师通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,main(主、从）
     * @return
     */
    @RequestMapping(value="/getCourse/teacher",method = RequestMethod.GET)
    public List<CourseTeacherVO> teacherGetCourse(@RequestParam("teacherId") Long teacherId)
    {
        return courseService.teacherGetCourse(BigInteger.valueOf(teacherId));
    }

    /**
     * @cyq
     * 学生通过jwt里的id，获得个人课程信息列表，包括courseId,courseName,klassId,klassName(Grade+KlassSerial)
     * @return
     */
    @RequestMapping(value="getCourse/student",method = RequestMethod.GET)
    public List<CourseStudentVO> studentGetCourse(@RequestParam("studentId") Long studentId)
    {
        return courseService.studentGetCourse(BigInteger.valueOf(studentId));
    }


    @RequestMapping(value="/course",method = RequestMethod.POST)
    public boolean creatCourse(@RequestBody Map<String,Object> courseMap) throws ParseException {
        int flag= courseService.creatCourse(courseMap);
        if(flag>0)return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    public Course getCourseByCourseId(@PathVariable("courseId") Long courseId){
        return courseService.getCourseByCourseId(BigInteger.valueOf(courseId));
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    public boolean deleteCourseByCourseId(@PathVariable("courseId") Long courseId) {
        int flag=courseService.deleteCourseByCourseId(BigInteger.valueOf(courseId));
        if(flag>0)return true;
        else return false;
    }

    /**
     * 获得课程下的所有轮次
     * @param courseId
     * @return
     * @throws RoundNotFindException
     */
    @RequestMapping(value="/course/{courseId}/round",method = RequestMethod.GET)
    @ResponseBody
    public  List<Round> listRoundByCourseId(@PathVariable("courseId") Long courseId) throws RoundNotFindException {
        List<Round> roundList=courseService.listRoundByCourseId(BigInteger.valueOf(courseId));
        if(roundList==null){
            throw new RoundNotFindException("未找到该课程下的讨论课轮次");
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
    public RoundVO getRoundByRoundId(@PathVariable("roundId") Long roundId,
                                     @RequestParam("courseId") Long courseId)
    {
        return courseService.getRoundByRoundId(
                BigInteger.valueOf(courseId),
                BigInteger.valueOf(roundId));
    }

    /**
     * @cyq
     * 根据roundId修改轮次信息（修改轮次的评分方式, 修改本轮各个班级允许的报名次数）
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@PathVariable("roundId") Long roundId,
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
    public BigInteger isBeingPresentSeminar(@PathVariable("teacherId")Long teacherId){
           return courseService.isBeingPresentSeminar(BigInteger.valueOf(teacherId));
    }

//    @GetMapping("/{studentId}/course")
//    public  List<StudentCourseVO> listKlassStudentByStudentId(@PathVariable("studentId") BigInteger studentId){
//        return courseService.listKlassStudentByStudentId(studentId);
//    }


    @PostMapping("/uploadStudentNameList")
    public String add(@RequestParam("file")MultipartFile file){
        //Map<Integer, Map<Integer,Object>> map = uploadExcelService.addCustomerInfo(file);
        uploadExcelService.addCustomerInfo(file);
        //System.out.println(map);
        return "success";
    }


}