package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

}
