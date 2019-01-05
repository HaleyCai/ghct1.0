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

    Boolean updateSeminarScore(@Param("klassSeminarId") BigInteger klassSeminarId,
                               @Param("teamId") BigInteger teamId,
                               @Param("questionScore") Double questionScore);

    Boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);

    BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId);

    Boolean updateRoundScoreEnd(@Param("roundId") BigInteger roundId,
                                @Param("teamId") BigInteger teamId,
                                @Param("presentationScore1") Double presentationScore1,
                                @Param("questionScore1") Double questionScore1,
                                @Param("reportScore1") Double reportScore1,
                                @Param("totalScore1") Double totalScore1);

    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);

    BigInteger getTeamIdByStudentId(BigInteger studentId);

}