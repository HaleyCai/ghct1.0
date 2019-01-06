package xmu.ghct.crm.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.vo.ScoreVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.vo.SeminarScoreVO;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
@Mapper
@Repository
public interface ScoreMapper {


    /**
     * 根据roundId获得该轮次下所有小组成绩
     * @param roundId
     * @return
     */
    List<ScoreVO> getRoundScore(BigInteger roundId);

    /**
     * 删除id为seminarId下的所有讨论课成绩
     * @param seminarId
     * @return
     */
    int deleteSeminarScoreBySeminarId(BigInteger seminarId);


    /**
     * 根据seminarId和teamId更新讨论课成绩
     * @param score
     * @return
     */
    int updateSeminarScoreBySeminarIdAndTeamId(Score score);


    /**
     * 根据seminarId和teamId获得讨论课成绩
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId);

    /**
     * 根据roundId和teamId获得小组轮次成绩
     * @param roundId
     * @param teamId
     * @return
     */
    ScoreVO getTeamRoundScore(BigInteger roundId,BigInteger teamId);

    /**
     * 根据roundId和teamId修改轮次成绩
     * @param scoreVO
     * @return
     */
    int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO);

    /**
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    List<ScoreVO> listRoundScoreByRoundId(BigInteger roundId);


    /**
     * 创建轮次成绩
     * @param scoreVO
     * @return
     */
    int insertRoundScore(ScoreVO scoreVO);


    /**
     *创建讨论课成绩
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    int insertSeminarScore(BigInteger klassSeminarId,BigInteger teamId);
}
