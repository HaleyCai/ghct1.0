package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class KlassService {

    @Autowired
    KlassDao klassDao;
    @Autowired
    RoundDao roundDao;

    public List<Klass> listKlassByCourseId(BigInteger courseId){
        return klassDao.listKlassByCourseId(courseId);
    }

    public int creatKlass(BigInteger courseId, Map<String,Object> klassMap)  {
        Klass klass=new Klass();
        klass.setKlassId(new BigInteger(klassMap.get("klassId").toString()));
        klass.setCourseId(new BigInteger(courseId.toString()));
        klass.setGrade(new Integer(klassMap.get("grade").toString()));
        klass.setKlassSerial(new Integer(klassMap.get("klassSerial").toString()));
        klass.setKlassTime(klassMap.get("klassTime").toString());
        klass.setKlassLocation(klassMap.get("klassLocation").toString());
        return klassDao.creatKlass(klass);
    }

    /**
     * 删除courseId下的所有班级
     * @param courseId
     * @return
     */
    public int deleteKlassByCourseIdAndKlassId(BigInteger courseId){
        return klassDao.deleteKlassByCourseIdAndKlassId(courseId);
    }

    /**
     * 根据roundId获取轮次的信息
     * @param roundId
     * @return
     */
    public Round getRoundByRoundId(BigInteger roundId)
    {
        return roundDao.getRoundByRoundId(roundId);
    }

    /**
     * 根据roundId修改轮次的信息（成绩评定方式）
     * @param inMap
     * @return
     */
    public boolean modifyRoundByRoundId(Map<String,Object> inMap)
    {
        Round round=new Round();
        round.setId((BigInteger) inMap.get("roundId"));
        round.setPresentationScoreMethod((int)inMap.get("presentationScoreMethod"));
        round.setReportScoreMethod((int)inMap.get("reportScoreMethod"));
        round.setQuestionScoreMethod((int)inMap.get("questionScoreMethod"));
        return roundDao.modifyRoundByRoundId(round);
    }
}
