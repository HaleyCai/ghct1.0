package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.KlassInfoVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.VO.SeminarVO;
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

    @RequestMapping(value="/course",method = RequestMethod.POST)
    public boolean creatCourse(@RequestBody Map<String,Object> courseMap) throws ParseException {
        int flag= courseService.creatCourse(courseMap);
        if(flag>0)return true;
        else return false;
    }

    @RequestMapping(value="/course",method = RequestMethod.GET)
    public List<Course> listCourseByTeacherId(@RequestBody Map<String,Object> teacherIdMap){
                List<Course> courseList=courseService.listCourseByTeacherId(teacherIdMap);
                return courseList;
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public CourseVO getCourseByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteCourseByCourseId(@PathVariable("courseId")BigInteger courseId) {
        int flag=courseService.deleteCourseByCourseId(courseId);
        if(flag>0)return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/round",method = RequestMethod.GET)
    @ResponseBody
    public  List<Round> listRoundByCourseId(@PathVariable("courseId") BigInteger courseId) throws RoundNotFindException {
        List<Round> roundList=courseService.listRoundByCourseId(courseId);
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
        Map<String,Integer> enroll=new TreeMap<>();
        Class<?> clazz = inMap.get("enrollNum").getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            System.out.println("fieldName "+ fieldName);
            Integer value = Integer.valueOf(field.get(inMap.get("enrollNum")).toString());
            System.out.println("value "+ value);
            enroll.put(fieldName, value);
        }
        System.out.println("map:   "+enroll);
        roundVO.setEnrollNum(enroll);
        //修改本轮各个班级允许的报名次数
        return courseService.modifyRoundByRoundId(roundVO);
    }

    /**
     * @cyq
     * 学生界面，根据学生id获得所有课程\班级信息
     * @return
     */
    /*
    @RequestMapping(value = "/course/mycourse",method = RequestMethod.GET)
    public List<KlassInfoVO> getKlassInfoByStudentId(@RequestBody Map<String,Object> inMap){

    }
    */
}