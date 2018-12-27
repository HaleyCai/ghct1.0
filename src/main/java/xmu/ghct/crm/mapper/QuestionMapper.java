package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component

public interface QuestionMapper {

    List<QuestionVO> getAllQuestion(BigInteger klassSeminarId);

    BigInteger getTeamIdByStudentId(BigInteger studentId,BigInteger klassId);

    BigInteger getKlassSeminarId(BigInteger klassId,BigInteger seminarId);

    int postQuestion(Question question);

    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);

    /**
     * @author hzm
     * 根据klassSeminarId 和 attendanceId获取提问信息
     * @param klassSeminarId
     * @param attendanceId
     * @return
     */
    List<Question> listQuestionByKlassSeminarIdAndAttendanceId(BigInteger klassSeminarId,BigInteger attendanceId);
}
