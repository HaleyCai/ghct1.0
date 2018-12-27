package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.QuestionDao;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;

import java.util.List;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<QuestionVO> getAllQuestion(BigInteger klassId,BigInteger seminarId)
    {
        return questionDao.getAllQuestion(questionDao.getKlassSeminarId(klassId,seminarId));
    }


    public int postQuestion(Map<String,Object> questionMap)
    {
        Question question=new Question();
        question.setTeamId(questionDao.getTeamIdByStudentId(new BigInteger(questionMap.get("studentId").toString()),new BigInteger(questionMap.get("klassId").toString())));
        question.setKlassSeminarId(questionDao.getKlassSeminarId(new BigInteger(questionMap.get("klassId").toString()),new BigInteger(questionMap.get("seminarId").toString())));
        question.setStudentId(new BigInteger(questionMap.get("studentId").toString()));
        return questionDao.postQuestion(question);
    }

    public boolean updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore)
    {
        boolean success;
        success=questionDao.updateQuestionScoreByQuestionId(questionId,questionScore);
        return success;
    }


    public List<Question> listQuestionByKlassSeminarIdAndAttendanceId(BigInteger klassSeminarId,BigInteger attendanceId){
        return questionDao.listQuestionByKlassSeminarIdAndAttendanceId(klassSeminarId,attendanceId);
    }
}
