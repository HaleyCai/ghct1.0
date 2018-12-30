package xmu.ghct.crm.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.dao.StudentDao;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.math.BigInteger;
import java.util.*;

public class GreetingController {

    @Autowired
    StudentDao studentDao;

    private BigInteger attendanceId;
    private static Map<BigInteger,Queue<QuestionListVO>> questionQueueList=new HashMap<>(0);
    private static Map<BigInteger,Set<BigInteger>> studentIdRecorderList=new HashMap<>(0);

    public GreetingController(BigInteger attendanceId){
        this.attendanceId=attendanceId;
        Queue<QuestionListVO> questionQueue=new LinkedList<>();
        Set<BigInteger> studentIDRecorder=new TreeSet<>();
        questionQueueList.put(attendanceId,questionQueue);
        studentIdRecorderList.put(attendanceId,studentIDRecorder);
    }

    public QuestionListVO getOneQuestion()
    {
        if(questionQueueList.get(attendanceId).isEmpty()){
            return null;
        }
        QuestionListVO question=questionQueueList.get(attendanceId).peek();
        while(studentIdRecorderList.get(attendanceId).contains(question.getStudentId()))
        {
            questionQueueList.get(attendanceId).offer(question);
            questionQueueList.get(attendanceId).poll();
            questionQueueList.get(attendanceId).remove(question.getStudentId());
            question=questionQueueList.get(attendanceId).peek();
            try{
                greeting();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        question.setSelected(1);
        return question;
    }

    public Queue<QuestionListVO> getQuestionQueue()
    {
        return questionQueueList.get(attendanceId);
    }

    public boolean postQuestion(QuestionListVO question)
    {
        if(studentIdRecorderList.get(attendanceId).contains(question.getStudentId())){
            return false;
        }
        questionQueueList.get(attendanceId).offer(question);
        studentIdRecorderList.get(attendanceId).add(question.getStudentId());
        try{
            greeting();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }


    @MessageMapping("/websocket/{seminarID}/{klassID}")
    @SendTo("topic/greetings/all/{seminarID}")
    public Map<String,Object> greeting()throws Exception{
        Map<String,Object> map=new HashMap<>(0);
        map.put("questionNumber",questionQueueList.get(attendanceId).size());
        return map;
    }


}