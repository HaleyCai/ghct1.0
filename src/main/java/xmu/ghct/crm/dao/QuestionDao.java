package xmu.ghct.crm.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.QuestionMapper;
import xmu.ghct.crm.entity.Question;
import java.math.BigInteger;
import java.util.List;

@Component
public class QuestionDao {
    @Autowired
    private QuestionMapper questionMapper;

    public List<Question> listQuestionByKlassSeminarIdAndAttendanceId(BigInteger klassSeminarId,BigInteger attendanceId)
    {
        return questionMapper.listQuestionByKlassSeminarIdAndAttendanceId(klassSeminarId,attendanceId);
    }

    public BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId)
    {
        return questionMapper.getKlassIdByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getTeamIdByQuestionId(BigInteger questionId)
    {
        return questionMapper.getTeamIdByQuestionId(questionId);
    }

    public int getKlassSerialByKlassId(BigInteger klassId)
    {
        return questionMapper.getKlassSerialByKlassId(klassId);
    }

    public int getTeamSerialByTeamId(BigInteger teamId)
    {
        return questionMapper.getTeamSerialByTeamId(teamId);
    }

    public String getStudentNameByStudentId(BigInteger studentId)
    {
        return questionMapper.getStudentNameByStudentId(studentId);
    }

    public boolean updateQuestionSelected(BigInteger questionId)
    {
        return questionMapper.updateQuestionSelected(questionId);
    }

    public Question getQuestionByQuestionId(BigInteger questionId)
    {
        return questionMapper.getQuestionByQuestionId(questionId);
    }

    public boolean postQuestion(Question question)
    {
        int v1=questionMapper.postQuestion(question);
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }

    public Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId)
    {
        return questionMapper.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
    }

    public Score updateSeminarScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore)
    {
        return questionMapper.updateSeminarScore(klassSeminarId,teamId,questionScore);
    }

    public Score updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore)
    {
        return questionMapper.updateSeminarScoreEnd(klassSeminarId,teamId,totalScore);
    }

    public BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId)
    {
        return questionMapper.getSeminarIdByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getRoundIdBySeminarId(BigInteger seminarId)
    {
        return questionMapper.getRoundIdBySeminarId(seminarId);
    }


    public boolean updateRoundScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore)
    {
        return questionMapper.updateRoundScoreEnd(klassSeminarId,teamId,totalScore);
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

    public BigInteger getTeamIdByStudentId(BigInteger studentId)
    {
        return questionMapper.getTeamIdByStudentId(studentId);
    }


    public int countQuestionNumber(BigInteger klassSeminarId,BigInteger attendanceId)
    {
        return questionMapper.countQuestionNumber(klassSeminarId,attendanceId);
    }
}