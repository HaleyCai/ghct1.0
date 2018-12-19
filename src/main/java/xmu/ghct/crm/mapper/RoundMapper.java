package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface RoundMapper {

    /**
     * 根据roundId获取该轮次下的所有讨论课
     * @param roundId
     * @return
     */
    List<Seminar> getSeminarByRoundId(@Param("roundId") BigInteger roundId);
}
