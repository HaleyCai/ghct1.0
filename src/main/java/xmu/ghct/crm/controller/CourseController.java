package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.RoundNotFindException;
import xmu.ghct.crm.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public RoundVO getRoundByRoundId(@PathVariable("roundId") BigInteger roundId)
    {
        return courseService.getRoundByRoundId(roundId);
    }

    /**
     * @cyq
     * 根据roundId修改轮次信息（只修改轮次的评分方式）
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@PathVariable("roundId") BigInteger roundId,@RequestBody Map<String,Object> inMap)
    {
        RoundVO roundVO=new RoundVO();
        roundVO.setRoundId((BigInteger) inMap.get("roundId"));
        roundVO.setPresentationScoreMethod(inMap.get("presentation").toString());
        roundVO.setQuestionScoreMethod(inMap.get("question").toString());
        roundVO.setReportScoreMethod(inMap.get("report").toString());
        return courseService.modifyRoundByRoundId(roundVO);
    }

    /**
     * @cyq
     * 自己加的api，修改本轮各个班级允许的报名次数，见页面“轮次设置”，最大值为该轮次下seminar的数量
     * @param inMap
     * @return
     */
    @RequestMapping(value = "/round/{roundId}/{klassId}/signtimes",method = RequestMethod.PUT)
    public boolean modifyRoundSignTimes(@RequestBody Map<String,Object> inMap)
    {
        return true;
    }



}