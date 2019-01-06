package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.vo.SeminarVO;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author gfj
 */
@Mapper
@Repository
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
     * 根据klassId和seminarId修改班级里讨论课报告提交ddl
     * @param klassSeminarId
     * @param reportDDL
     * @return
     */
    int updateKlassSeminarByKlassSeminarId(BigInteger klassSeminarId, Date reportDDL);


    /**
     * 根据klassId和seminarId删除班级讨论课
     * @param klassId
     * @param seminarId
     * @return
     */
    int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId, BigInteger seminarId);

    /**
     * 根据klassId和semianrId获取班级讨论课信息
     * @param klassId
     * @param seminarId
     * @return
     */
    SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId,BigInteger seminarId);


    /**
     * 修改班级讨论课的讨论课状态
     * @param klassSeminarId
     * @param status
     * @return
     */
    int updateKlassSeminarStatus(BigInteger klassSeminarId,Integer status);


    /**
     * 根据seminarId获得讨论课所属轮次id
     * @param seminarId
     * @return
     */
    BigInteger getRoundIdBySeminarId(BigInteger seminarId);


    /**
     * 根据klassId & seminarId 获得klassSeminarId
     * @param seminarId
     * @param klassId
     * @return
     */
    BigInteger getKlassSeminarIdBySeminarIdAndKlassId(BigInteger seminarId,BigInteger klassId);


    /**
     * 根据klassSeminarId获得班级讨论课信息
     * @param klassSeminarId
     * @return
     */
    SeminarVO getKlassSeminarByKlassSeminarId(BigInteger klassSeminarId);


    /**
     * 根据klassSeminarId获得klassId
     * @param klassSeminarId
     * @return
     */
    BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId);


    /**
     * 获取某班级下所有班级讨论课
     * @param klassId
     * @return
     */
    List<SeminarVO> listKlassSeminarIdByKlassId(BigInteger klassId);


    /**
     * 创建班级讨论课(klass_seminar)
     * @param seminarId
     * @param klassId
     * @return
     */
    int insertKlassSeminarBySeminarIdAndKlassId(BigInteger seminarId,BigInteger klassId);

    /**
     * 获取某课程某轮次的讨论课
     * @param courseId
     * @param roundId
     * @return
     */
    Seminar getSeminarByCourseIdAndRoundId(BigInteger courseId,BigInteger roundId);

    /**
     * 获取讨论课所属班级ID
     * @param seminarId
     * @return
     */
    List<BigInteger>listKlassIdBySeminarId(BigInteger seminarId);

}