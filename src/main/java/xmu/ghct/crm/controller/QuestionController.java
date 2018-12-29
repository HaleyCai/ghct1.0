package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.service.QuestionService;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     *前端传入klassId、seminarId和attendanceId,教师提问界面右边显示所有提问学生
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/{attendanceId}/question",
            method = RequestMethod.GET)
    public List<QuestionListVO> getAllQuestion(@PathVariable("seminarId") BigInteger seminarId,
                                               @PathVariable("klassId") BigInteger klassId,
                                               @PathVariable("attendanceId") BigInteger attendanceId)

    {
        return questionService.getAllQuestion(
                seminarId,
                klassId,
                attendanceId);
    }

    /**
     * 被抽取到提问，展示提问人信息
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/attendanceId/{attendanceId}/question",method = RequestMethod.GET)
    public QuestionListVO getOneQuestion(@PathVariable("seminarId") BigInteger seminarId,
                                         @PathVariable("klassId") BigInteger klassId,
                                         @PathVariable("attendanceId") BigInteger attendanceId)
    {
        return questionService.getOneQuestion(
                seminarId,
                klassId,
                attendanceId);
    }


    /**
     * 前端传入klassId,seminarId和attendanceId，发布提问，studentId通过jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/{attendanceId}/question",
            method = RequestMethod.POST)
    public boolean postQuestion(@PathVariable("seminarId") BigInteger seminarId,
                                @PathVariable("klassId") BigInteger klassId,
                                @PathVariable("attendanceId") BigInteger attendanceId,
                                @RequestBody Map<String,Object> inMap)
    {
        return questionService.postQuestion(
                seminarId,
                klassId,
                attendanceId,
                new BigInteger(inMap.get("studentId").toString())
                );

    }


    /**
     * 给提问打分
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/seminarId/{seminarId}/{klassId}/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScore(@PathVariable("seminarId") BigInteger seminarId,
                                       @PathVariable("klassId") BigInteger klassId,
                                       @PathVariable("questionId") BigInteger questionId,
                                       @RequestBody Map<String,Object> inMap)
    {
        return questionService.updateQuestionScore(
                seminarId,
                klassId,
                questionId,
                new Double(inMap.get("questionScore").toString()));
    }

}