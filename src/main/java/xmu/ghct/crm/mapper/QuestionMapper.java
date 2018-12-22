package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.QuestionVO;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component

public interface QuestionMapper {

    List<QuestionVO> getAllQuestion(BigInteger seminarId,BigInteger classId);

    int postQuestion(QuestionVO questionVO);

    int updateQuestionScoreByQuestionId(BigInteger questionId);
}
