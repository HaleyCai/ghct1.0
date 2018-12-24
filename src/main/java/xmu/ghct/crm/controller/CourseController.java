package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.RoundNotFindException;
import xmu.ghct.crm.service.CourseService;
import xmu.ghct.crm.service.ImportService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    ImportService importService;

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

    @RequestMapping(value = "singleFileUpload",method = RequestMethod.POST)
    public void singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath="C:/Users/huangzhenmin/Desktop/";
        File fileDir = new File(filePath+fileName);
        file.transferTo(fileDir);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = ( MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            //TODO 随意发挥
            System.out.println(lo);
        }
        return "上传成功";
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
     * 自己加的api，修改本轮各个班级允许的报名次数，见页面“轮次设置”
     * @param inMap
     * @return
     */
    @RequestMapping(value = "/round/{roundId}/{klassId}/signtimes",method = RequestMethod.PUT)
    public boolean modifyRoundSignTimes(@RequestBody Map<String,Object> inMap)
    {
        return true;
    }


}