package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.RoundMapper;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */
@Component
public class RoundDao {

    @Autowired
    private RoundMapper roundMapper;

    /**
     * 根据roundId获取该轮次下所有seminar
     * @param roundId
     * @return
     */
    public List<Seminar> getSeminarByRoundId(BigInteger roundId){
        List<Seminar> list =roundMapper.getSeminarByRoundId(roundId);
        if(list==null){
            //throw
        }
        return list;
    }

    /**
     * 根据roundId获得轮次信息
     * @param roundId
     * @return
     */
    public Round getRoundByRoundId(BigInteger roundId){
        Round round=roundMapper.getRoundByRoundId(roundId);
        if(round==null){
            //throw
        }
        return round;
    }

    /**
     * 根据roundId修改轮次信息（成绩评定方式）
     * @param round
     * @return
     */
    public boolean modifyRoundByRoundId(Round round){
        int v1=roundMapper.modifyRoundByRoundId(round.getId(),round.getPresentationScoreMethod(),round.getReportScoreMethod(),round.getQuestionScoreMethod());
        if(v1<=0){
            //throw
            return false;
        }
        else
            return true;
    }
}
