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

    Score updateSeminarScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore);

    boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);

    BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId);

    BigInteger getRoundIdBySeminarId(BigInteger seminarId);


    Score getRoundScoreByRoundIdAndTeamId(BigInteger roundId,BigInteger teamId);


    Score updateRoundScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore);


    boolean updateRoundScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);


    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);


    BigInteger getTeamIdByStudentId(BigInteger studentId);

    int countQuestionNumber(@Param("klassSeminarId") BigInteger klassSeminarId, @Param("attendanceId") BigInteger attendanceId);

}