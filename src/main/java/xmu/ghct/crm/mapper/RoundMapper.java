package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface RoundMapper {

    /**
     * 根据roundId获得轮次信息
     * @param roundId
     * @return
     */
    Round getRoundByRoundId(@Param("roundId") BigInteger roundId);

    /**
     * 根据roundId修改轮次信息（成绩评定方式）
     * @param roundId
     * @param presentationScoreMethod
     * @param reportScoreMethod
     * @param questionScoreMethod
     * @return
     */
    int modifyRoundMethodByRoundId(@Param("roundId") BigInteger roundId,
                                   @Param("presentationScoreMethod") int presentationScoreMethod,
                                   @Param("reportScoreMethod") int reportScoreMethod,
                                   @Param("questionScoreMethod") int questionScoreMethod);

    /**
     * 根据courseId获得轮次
     * @param courseId
     * @return
     */
    List<Round> listRoundByCourseId(BigInteger courseId);

    /**
     * 创建轮次
     * @param round
     * @return
     */
    int insertRound(Round round);
}
