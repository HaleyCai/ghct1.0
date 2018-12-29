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
     *前端传入klassSeminarId和attendanceId,教师提问界面右边显示所有提问学生
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
            method = RequestMethod.GET)
    public List<QuestionListVO> getAllQuestion(@PathVariable("seminarId") BigInteger seminarId,
                                               @PathVariable("klassId") BigInteger klassId)
    {
        return questionService.getAllQuestion(
                seminarId,
                klassId);
    }

    /**
     * 教师点击下个提问时，修改当前提问为已抽到
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question/{question}",
            method = RequestMethod.PUT)
    public boolean updateQuestionSelected(@PathVariable("seminarId") BigInteger seminarId,
                                          @PathVariable("klassId") BigInteger klassId,
                                          @PathVariable("question") BigInteger question){
        return questionService.updateQuestionSelected(question);
    }

    /**
     * 学生提问界面，被抽取到提问，展示提问人信息
     */
    @RequestMapping(value="/question/{questionId}",method = RequestMethod.GET)
    public QuestionListVO getOneQuestion(@PathVariable("questionId") BigInteger questionId){
        return questionService.getOneQuestion(questionId);
    }

    /**
     * 前端传入lassSeminarId和attendanceId，发布提问，studentId通过jwt获得
     * @param inMap
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
            method = RequestMethod.POST)
    public boolean postQuestion(@PathVariable("seminarId") BigInteger seminarId,
                                @PathVariable("klassId") BigInteger klassId,
                                @RequestBody Map<String,Object> inMap)
    {
        return questionService.postQuestion(
                new BigInteger(inMap.get("studentId").toString()),
                seminarId,
                klassId);

    }

    /**
     *前端传入klassSeminarId和attendanceId,统计已提问学生数
     * @return
     */
    @RequestMapping(value="/teacher/seminar/{seminarId}/attendanceId/{attendanceId}",
            method = RequestMethod.GET)
    public int countQuestionNumber(@PathVariable("seminarId") BigInteger seminarId,
                                   @PathVariable("attendanceId") BigInteger attendanceId)
    {
        return questionService.countQuestionNumber(
                seminarId,
                attendanceId);
    }


    /**
     * 给提问打分
     * @param inMap
     * @return
     */
    @RequestMapping(value="/teacher/seminarId/{seminarId}/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScore(@PathVariable("seminarId") BigInteger seminarId,
                                       @PathVariable("questionId") BigInteger questionId,
                                       @RequestBody Map<String,Object> inMap){
        return questionService.updateQuestionScore(
                questionId,
                seminarId,
                new Double(inMap.get("questionScore").toString()));
    }






}