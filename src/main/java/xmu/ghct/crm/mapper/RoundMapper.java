package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */
@Mapper
@Repository
public interface RoundMapper {

    /**
     * 根据roundId获取该轮次下的所有讨论课
     * @param roundId
     * @return
     */
    List<Seminar> getSeminarByRoundId(BigInteger roundId);

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
    int modifyRoundByRoundId(@Param("roundId") BigInteger roundId,
                             @Param("courseId") BigInteger courseId,
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
     * 查找一个课程下，轮次的最大值
     * @param courseId
     * @return
     */
    Integer getRoundNumByCourseId(BigInteger courseId);

    /**
     * 默认创建允许报名次数为1
     * @param klassId
     * @param roundId
     * @return
     */
    int createDefaultEnrollNumber(BigInteger klassId, BigInteger roundId);

    /**
     * 获得某班级，某轮次最大报名次数
     * @param klassId
     * @param roundId
     * @return
     */
    Integer getEnrollNum(BigInteger klassId, BigInteger roundId);

    /**
     * 修改某轮，某班，最大报名次数
     * @param klassId
     * @param roundId
     * @param num
     * @return
     */
    int modifyEnrollNum(BigInteger klassId,BigInteger roundId,int num);

    /**
     * 获取轮次序号
     * @param roundId
     * @return
     */
    Integer getRoundSerialByRoundId(BigInteger roundId);

    /**
     * 获得某个轮次的信息
     * @param courseId
     * @param roundSerial
     * @return
     */
    Round getRoundByCourseIdAndRoundSerial(BigInteger courseId,int roundSerial);

    /**
     * 获取轮次所在课程ID
     * @param roundId
     * @return
     */
    BigInteger getCourseIdByRoundId(BigInteger roundId);
}
