package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Map;
import java.util.List;
@CrossOrigin
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     *前端传入klassSeminarId和attendanceId,教师提问界面右边显示所有提问学生
     * @return
     */
    @RequestMapping(value="/seminar/{klassSeminarId}/question",
            method = RequestMethod.GET)
    public List<QuestionListVO> getAllQuestion(@PathVariable("klassSeminarId") String klassSeminarId,
                                               @RequestParam String attendanceId){
        return questionService.getAllQuestion(
                new BigInteger(klassSeminarId),
                new BigInteger(attendanceId));
    }

    /**
     * 教师点击下个提问时，修改当前提问为已抽到,
     * @return
     */
    /**@RequestMapping(value="/seminar/klass/question",
            method = RequestMethod.PUT)
    public boolean updateQuestionSelected(@RequestParam String questionId){
        return questionService.updateQuestionSelected(
                new BigInteger(questionId));
    }**/

    /**
     * 学生提问界面，被抽取到提问，展示提问人信息（随机）
     */
    @RequestMapping(value="/attendanceId/{attendanceId}/question",method = RequestMethod.GET)
    public QuestionListVO getOneQuestion(@PathVariable("attendanceId")String attendanceId) throws NotFoundException {
        QuestionListVO questionListVO=questionService.getOneQuestion(
                new BigInteger(attendanceId));
        if(questionListVO!=null)
        {
            questionService.updateQuestionSelected(questionListVO.getQuestionId());
        }
        return questionListVO;
    }


    /**
     * 前端传入studentId，klassSeminarId和attendanceId，发布提问，不能提问本组的
     * @return
     */
    @RequestMapping(value="/seminar/klass/{klassSeminarId}/{attendanceId}/question",
            method = RequestMethod.POST)
    public boolean postQuestion(HttpServletRequest request,
                                @PathVariable("klassSeminarId")String klassSeminarId,
                                @PathVariable("attendanceId")String attendanceId) throws org.apache.ibatis.javassist.NotFoundException {
        BigInteger studentId=jwtTokenUtil.getIDFromRequest(request);
        return questionService.postQuestion(
                studentId,
                new BigInteger(klassSeminarId),
                new BigInteger(attendanceId));
    }


    /**
     * 给提问打分
     * @param inMap
     * @return
     */
    @RequestMapping(value="/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScore(@PathVariable("questionId")String questionId,
                                           @RequestBody Map<String,Object> inMap) throws NotFoundException {
        return questionService.updateQuestionScore(
                new BigInteger(questionId),
                new BigInteger(inMap.get("klassSeminarId").toString()),
                new Double(inMap.get("questionScore").toString()));
    }

}