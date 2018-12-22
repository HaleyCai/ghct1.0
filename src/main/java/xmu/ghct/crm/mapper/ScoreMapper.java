package xmu.ghct.crm.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
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
}
