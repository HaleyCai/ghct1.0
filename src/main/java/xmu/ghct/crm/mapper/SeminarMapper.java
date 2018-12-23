package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SeminarMapper {

    /**
     * 创建讨论课
     * @param seminar
     * @return
     */
    int creatSeminar(Seminar seminar);

    /**
     * 根据roundId获得讨论课ID
     * @param roundId
     * @return
     */
    List<BigInteger> getSeminarIdByRoundId(BigInteger roundId);

    /**
     * 根据seminarId修改讨论课
     * @param seminar
     * @return
     */
    int updateSeminarBySeminarId(Seminar seminar);

    /**
     * 根据seminarId删除讨论课
     * @param seminarId
     * @return
     */
    int deleteSeminarBySeminarId(BigInteger seminarId);

    /**
     * 根据seminarId删除klass_seminar表相关记录
     * @param seminarId
     * @return
     */
    int deleteKlassSeminarBySeminarId(BigInteger seminarId);


    /**
     * 根据seminarId获得讨论课信息
     * @param seminarId
     * @return
     */
    Seminar getSeminarBySeminarId(BigInteger seminarId);

    /**
     * @author hzm
     * 根据klassId和seminarId修改班级里讨论课报告提交ddl
     * @param klassId
     * @param seminarId
     * @return
     */
    int updateKlassSeminarBySeminarIdAndKlassId(BigInteger klassId, BigInteger seminarId, Date reportDDL);


    /**
     * @author hzm
     * 根据klassId和seminarId删除班级讨论课
     * @param klassId
     * @param seminarId
     * @return
     */
    int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId, BigInteger seminarId);

    /**
     * @author hzm
     * 根据klassId和semianrId获取班级讨论课信息
     * @param klassId
     * @param seminarId
     * @return
     */
    SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId,BigInteger seminarId);


    /**
     * @author hzm
     * 修改班级讨论课的讨论课状态
     * @param seminarVO
     * @return
     */
    int updateKlassSeminarStatus(SeminarVO seminarVO);
}