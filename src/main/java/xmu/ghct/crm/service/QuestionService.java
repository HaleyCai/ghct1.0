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

    public List<QuestionVO> getAllQuestion(BigInteger seminarId,BigInteger classId)
    {
        return questionDao.getAllQuestion(seminarId,classId);
    }

    public int postQuestion(Map<String,Object> questionMap)
    {
        QuestionVO questionVO=new QuestionVO();
        questionVO.setQuestionId(new BigInteger(questionMap.get("questionId").toString()));
        questionVO.setTeamId(new BigInteger(questionMap.get("teamId").toString()));
        questionVO.setTeamName(questionMap.get("teamName").toString());
        questionVO.setSeminarId(new BigInteger(questionMap.get("seminarId").toString()));
        questionVO.setKlassId(new BigInteger(questionMap.get("klassId").toString()));
        questionVO.setStudentId(new BigInteger(questionMap.get("studentId").toString()));
        return questionDao.postQuestion(questionVO);
    }

    public boolean updateQuestionScoreByQuestionId(BigInteger questionId)
    {
        boolean success;
        success=questionDao.updateQuestionScoreByQuestionId(questionId);
        return success;
    }
}
