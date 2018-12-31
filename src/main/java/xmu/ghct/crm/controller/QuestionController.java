package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     *前端传入klassId、seminarId和attendanceId,教师提问界面右边显示所有提问学生
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/{attendanceId}/question",
            method = RequestMethod.GET)
    public List<QuestionListVO> getAllQuestion(@PathVariable("seminarId") Long seminarId,
                                               @PathVariable("klassId") Long klassId,
                                               @PathVariable("attendanceId") Long attendanceId)

    {
        return questionService.getAllQuestion(
                BigInteger.valueOf(seminarId),
                BigInteger.valueOf(klassId),
                BigInteger.valueOf(attendanceId));
    }

    /**
     * 被抽取到提问，展示提问人信息
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/attendanceId/{attendanceId}/question",method = RequestMethod.GET)
    public QuestionListVO getOneQuestion(@PathVariable("seminarId") Long seminarId,
                                         @PathVariable("klassId") Long klassId,
                                         @PathVariable("attendanceId") Long attendanceId)
    {
        return questionService.getOneQuestion(
                BigInteger.valueOf(seminarId),
                BigInteger.valueOf(klassId),
                BigInteger.valueOf(attendanceId));
    }


    /**
     * 前端传入klassId,seminarId和attendanceId，发布提问，studentId通过jwt获得
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/{attendanceId}/question",
            method = RequestMethod.POST)
    public boolean postQuestion(HttpServletRequest request,
                                @PathVariable("seminarId") Long seminarId,
                                @PathVariable("klassId") Long klassId,
                                @PathVariable("attendanceId") Long attendanceId)
    {
        BigInteger studentId=jwtTokenUtil.getIDFromRequest(request);
        return questionService.postQuestion(
                BigInteger.valueOf(seminarId),
                BigInteger.valueOf(klassId),
                BigInteger.valueOf(attendanceId),
                studentId
                );

    }


    /**
     * 给提问打分
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/seminarId/{seminarId}/{klassId}/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScore(@PathVariable("seminarId") Long seminarId,
                                       @PathVariable("klassId") Long klassId,
                                       @PathVariable("questionId") Long questionId,
                                       @RequestBody Map<String,Object> inMap)
    {
        return questionService.updateQuestionScore(
                BigInteger.valueOf(seminarId),
                BigInteger.valueOf(klassId),
                BigInteger.valueOf(questionId),
                new Double(inMap.get("questionScore").toString()));
    }

}