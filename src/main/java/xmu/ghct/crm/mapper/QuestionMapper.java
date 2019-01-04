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


    BigInteger getTeamIdByQuestionId(BigInteger questionId);

    String getStudentNameByStudentId(BigInteger studentId);

    boolean updateQuestionSelected(BigInteger questionId);

    Question getOneQuestion(BigInteger attendanceId);

    int postQuestion(Question question);

    Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId, BigInteger teamId);

    Boolean updateSeminarScore(BigInteger klassSeminarId,BigInteger teamId,Double questionScore);

    Boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);

    BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId);

    Boolean updateRoundScoreEnd(BigInteger roundId,BigInteger teamId,Double presentationScore1,Double questionScore1,Double reportScore1,Double totalScore1);

    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);

    BigInteger getTeamIdByStudentId(BigInteger studentId);

}