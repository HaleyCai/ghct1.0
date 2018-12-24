package xmu.ghct.crm.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface ScoreMapper {

    /**
     * 根据teamId获得成绩信息
     * @param teamId
     * @return
     */
    List<Score> listScoreByTeamId(BigInteger teamId);

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
     * @author hzm
     * 根据seminarId和teamId更新讨论课成绩
     * @param score
     * @return
     */
    int updateSeminarScoreBySeminarIdAndTeamId(Score score);


    /**
     * @author hzm
     * 根据seminarId和teamId获得讨论课成绩
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    Score getSeminarScoreBySeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId);

    /**
     * @author hzm
     * 根据roundId和teamId获得小组轮次成绩
     * @param roundId
     * @param teamId
     * @return
     */
    ScoreVO getTeamRoundScore(BigInteger roundId,BigInteger teamId);

    /**
     * @author hzm
     * 根据roundId和teamId修改轮次成绩
     * @param scoreVO
     * @return
     */
    int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO);

    /**
     * @cyq
     * 获取一个队伍的一个轮次的总成绩
     * @param roundId
     * @param teamId
     * @return
     */
    ScoreVO getRoundScoreByRoundIdAndTeamId(BigInteger roundId,BigInteger teamId);
}
