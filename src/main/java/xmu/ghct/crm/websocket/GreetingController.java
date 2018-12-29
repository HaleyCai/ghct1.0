package xmu.ghct.crm.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.dao.TeamDao;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.entity.Question;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.math.BigInteger;
import java.util.*;

public class GreetingController {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;

    private BigInteger attendanceId;
    private static Map<BigInteger,Queue<QuestionListVO>> questionQueueList=new HashMap<>(0);
    private static Map<BigInteger,Set<BigInteger>> studentIDRecorderList=new HashMap<>(0);

    public GreetingController(BigInteger attendanceId){
        this.attendanceId=attendanceId;
        Queue<QuestionListVO> questionQueue=new LinkedList<>();
        Set<BigInteger> studentIDRecorder=new TreeSet<>();
        questionQueueList.put(attendanceId,questionQueue);
        studentIDRecorderList.put(attendanceId,studentIDRecorder);
    }

    public QuestionListVO getQuestion()
    {
        if(questionQueueList.get(attendanceId).isEmpty()){
            return null;
        }
        QuestionListVO question=questionQueueList.get(attendanceId).peek();
        while(studentIDRecorderList.get(attendanceId).contains(question.getStudentId()))
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
        if(studentIDRecorderList.get(attendanceId).contains(question.getStudentId())){
            return false;
        }
        questionQueueList.get(attendanceId).offer(question);
        studentIDRecorderList.get(attendanceId).add(question.getStudentId());
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