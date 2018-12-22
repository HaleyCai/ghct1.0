package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.VO.CreatCourseVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.service.CourseService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping(value="/course/creatCourse",method = RequestMethod.POST)
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

    @RequestMapping(value="/course/searchCourse/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public CreatCourseVO getCourseByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/deleteCourse/{courseId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteCourseByCourseId(@PathVariable("courseId")BigInteger courseId) {
        int flag=courseService.deleteCourseByCourseId(courseId);
        if(flag>0)return true;
        else return false;
    }


    @RequestMapping(value = "singleFileUpload",method = RequestMethod.POST)
    public void singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath="C:/Users/huangzhenmin/Desktop/";
        File fileDir = new File(filePath+fileName);
        file.transferTo(fileDir);
    }

    /**
     * 根据roundId获取轮次信息
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.GET)
    public RoundVO getRoundByRoundId(@PathVariable("roundId") BigInteger roundId)
    {
        return courseService.getRoundByRoundId(roundId);
    }

    /**
     * 根据roundId修改轮次的成绩评定方式
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@PathVariable("roundId") BigInteger roundId,@RequestBody Map<String,Object> inMap)
    {
        RoundVO roundVO=new RoundVO();
        roundVO.setRoundId((BigInteger) inMap.get("roundId"));
        roundVO.setPresentationScoreMethod(inMap.get("presentationScoreMethod").toString());
        roundVO.setReportScoreMethod(inMap.get("reportScoreMethod").toString());
        roundVO.setQuestionScoreMethod(inMap.get("questionScoreMethod").toString());
        return courseService.modifyRoundMethodByRoundId(roundVO);
    }

    /**
     * 在课程下，创建轮次，前端传参courseId，轮次序号order
     * @param inMap
     * @return 创建成功，返回轮次id，否则返回-1
     */
    @RequestMapping(value="/round",method = RequestMethod.POST)
    public BigInteger createRound(@RequestBody Map<String,Object> inMap)
    {
        Round round=new Round();
        round.setCourseId((BigInteger)inMap.get("courseId"));
        round.setRoundSerial((int)inMap.get("order"));
        round.setPresentationScoreMethod(0);
        round.setReportScoreMethod(0);
        round.setQuestionScoreMethod(0);
        return courseService.createRound(round);
    }
}