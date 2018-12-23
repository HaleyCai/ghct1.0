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
     * @cyq
     * 根据roundId获取该轮次下的所有讨论课
     * @param roundId
     * @return
     */
    List<Seminar> getSeminarByRoundId(BigInteger roundId);

    /**
     * @cyq
     * 根据roundId获得轮次信息
     * @param roundId
     * @return
     */
    Round getRoundByRoundId(@Param("roundId") BigInteger roundId);

    /**@cyq
     * 根据roundId修改轮次信息（成绩评定方式）
     * @param roundId
     * @param presentationScoreMethod
     * @param reportScoreMethod
     * @param questionScoreMethod
     * @return
     */
    int modifyRoundByRoundId(@Param("roundId") BigInteger roundId,
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

    /**
     * @cyq
     * 查找一个课程下，轮次的最大值
     * @param courseId
     * @return
     */
    int getRoundNumByCourseId(BigInteger courseId);

    /**
     * @cyq
     * 默认创建允许报名次数为1
     * @param classId
     * @param roundId
     * @return
     */
    int createDefaultEnrollNumber(BigInteger classId, BigInteger roundId);

}
