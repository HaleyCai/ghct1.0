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
     * 根据teamId和seminarId获得讨论课成绩
     * @param seminarId
     * @param teamId
     * @return
     */
    Score getScoreBySeminarIdAndTeamId(BigInteger seminarId,BigInteger teamId);

    /**
     * 删除id为seminarId下的所有讨论课成绩
     * @param seminarId
     * @return
     */
    int deleteSeminarScoreBySeminarId(BigInteger seminarId);
}
