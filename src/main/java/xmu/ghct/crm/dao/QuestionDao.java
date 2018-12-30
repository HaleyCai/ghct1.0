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

    public Boolean updateSeminarScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore)
    {
        return questionMapper.updateSeminarScore(klassSeminarId,teamId,questionScore);
    }

    public Boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore)
    {
        return questionMapper.updateSeminarScoreEnd(klassSeminarId,teamId,totalScore);
    }

    public BigInteger getRoundIdBySeminarId(BigInteger seminarId)
    {
        return questionMapper.getRoundIdBySeminarId(seminarId);
    }

    public boolean updateRoundScoreEnd(BigInteger roundId,BigInteger teamId,Double presentationScore1,Double questionScore1,Double reportScore1,Double totalScore1)
    {
        return questionMapper.updateRoundScoreEnd(roundId,teamId,presentationScore1,questionScore1,reportScore1,totalScore1);
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


    public BigInteger getQuestionId(BigInteger klassSeminar,BigInteger attendanceId,BigInteger studentId)
    {
        return questionMapper.getQuestionId(klassSeminar,attendanceId,studentId);
    }

    public int countQuestionNumber(BigInteger klassSeminarId,BigInteger attendanceId)
    {
        return questionMapper.countQuestionNumber(klassSeminarId,attendanceId);
    }

    public BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId)
    {
        return questionMapper.getKlassIdByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId)
    {
        return questionMapper.getSeminarIdByKlassSeminarId(klassSeminarId);
    }

    public Question getQuestionByQuestionId(BigInteger questionId)
    {
        return questionMapper.getQuestionByQuestionId(questionId);
    }

}