package xmu.ghct.crm.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.mapper.QuestionMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class QuestionDao {
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionVO> getAllQuestion(BigInteger seminarId,BigInteger classId)
    {
        List<QuestionVO> resultUser=questionMapper.getAllQuestion(seminarId,classId);
        return resultUser;
    }

    public int postQuestion(QuestionVO questionVO)
    {
        return questionMapper.postQuestion(questionVO);
    }

    public boolean updateQuestionScoreByQuestionId(BigInteger questionId)
    {
        int v1=questionMapper.updateQuestionScoreByQuestionId(questionId);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }


}
