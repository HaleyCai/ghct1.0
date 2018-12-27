package xmu.ghct.crm.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.mapper.QuestionMapper;
import xmu.ghct.crm.entity.Question;
import java.math.BigInteger;
import java.util.List;

@Component
public class QuestionDao {
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionVO> getAllQuestion(BigInteger klassSeminarId)
    {
        List<QuestionVO> result=questionMapper.getAllQuestion(klassSeminarId);

        return result;
    }

    public BigInteger getTeamIdByStudentId(BigInteger studentId,BigInteger klassId)
    {
        return questionMapper.getTeamIdByStudentId(studentId,klassId);
    }

    public BigInteger getKlassSeminarId(BigInteger klassId,BigInteger seminarId)
    {
        return questionMapper.getKlassSeminarId(klassId,seminarId);
    }

    public int postQuestion(Question question)
    {
        return questionMapper.postQuestion(question);
    }

    public boolean updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore)
    {
        int v1=questionMapper.updateQuestionScoreByQuestionId(questionId,questionScore);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    public List<Question> listQuestionByKlassSeminarIdAndAttendanceId(BigInteger klassSeminarId,BigInteger attendanceId){
        return questionMapper.listQuestionByKlassSeminarIdAndAttendanceId(klassSeminarId,attendanceId);
    }


}
