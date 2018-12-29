package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;
import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component

public interface QuestionMapper {


    List<Question> listQuestionByKlassSeminarIdAndAttendanceId(BigInteger klassSeminarId,BigInteger attendanceId);

    BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId);

    BigInteger getTeamIdByQuestionId(BigInteger questionId);

    int getKlassSerialByKlassId(BigInteger klassId);

    int getTeamSerialByTeamId(BigInteger teamId);

    String getStudentNameByStudentId(BigInteger studentId);

    boolean updateQuestionSelected(BigInteger questionId);

    Question getQuestionByQuestionId(BigInteger questionId);

    int postQuestion(Question question);

    Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId, BigInteger teamId);

    Boolean updateSeminarScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore);

    Boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);

    BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId);

    BigInteger getRoundIdBySeminarId(BigInteger seminarId);

    Boolean updateRoundScoreEnd(BigInteger roundId,BigInteger teamId,Double presentationScore1,Double questionScore1,Double reportScore1,Double totalScore1);

    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);

    BigInteger getTeamIdByStudentId(BigInteger studentId);

    int countQuestionNumber(@Param("klassSeminarId") BigInteger klassSeminarId, @Param("attendanceId") BigInteger attendanceId);

}