package xmu.ghct.crm.service;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.DateDao;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.dao.SeminarDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SeminarService {
    @Autowired
    SeminarDao seminarDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    DateDao dateDao;

    public int creatSeminar(Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        if(seminarMap.get("roundId").toString()==""){
            Round round=new Round();
            round.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
            round.setPresentationScoreMethod(1);
            round.setQuestionScoreMethod(0);
            round.setReportScoreMethod(1);
            round.setRoundSerial(0);
            roundDao.insertRound(round);
            seminar.setRoundId(round.getRoundId());
        }else{
            seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        }
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        Date start = dateDao.transferToDateTime(seminarMap.get("enrollStartTime").toString());
        seminar.setEnrollStartTime(start);
        Date end = dateDao.transferToDateTime(seminarMap.get("enrollEndTime").toString());
        seminar.setEnrollEndTime(end);
        return seminarDao.creatSeminar(seminar);
    }


    /**
     * 获取某轮次下所有讨论课的简略信息
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId)
    {
        List<SeminarSimpleVO> list = roundDao.getSeminarByRoundId(roundId);
        if(list==null)
        {
            //throw
        }
        return list;
    }


    /**
     * 根据seminarId修改讨论课信息
     * @param seminarId
     * @return
     */
    public int updateSeminarBySeminarId(BigInteger seminarId,@RequestBody Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        Date enrollStartTime = dateDao.transferToDateTime(seminarMap.get("enrollStartTime").toString());
        seminar.setEnrollStartTime(enrollStartTime);
        Date enrollEndTime = dateDao.transferToDateTime(seminarMap.get("enrollEndTime").toString());
        seminar.setEnrollEndTime(enrollEndTime);
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setSeminarId(seminarId);
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        return seminarDao.updateSeminarBySeminarId(seminar);
    }


    public int deleteSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.deleteSeminarBySeminarId(seminarId);
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.deleteKlassSeminarBySeminarId(seminarId);
    }

    public  Seminar getSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.getSeminarBySeminarId(seminarId);
    }

    public int updateKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId,Map<String,Object> seminarMap) throws ParseException {
        Date reportDDL = dateDao.transferToDateTime(seminarMap.get("reportDDL").toString());
        return seminarDao.updateKlassSeminarBySeminarIdAndKlassId(klassId,seminarId,reportDDL);
    }

    public int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId){
        return seminarDao.deleteKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
    }

    /**
     * @author hzm
     * 根据seminarId和klassId获得班级讨论课信息
     * @param klassId
     * @param seminarId
     * @return
     */
    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId, BigInteger seminarId) {
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarId);
        seminarVO.setEnrollEndTime(seminar.getEnrollEndTime());
        seminarVO.setEnrollStartTime(seminar.getEnrollStartTime());
        seminarVO.setMaxTeam(seminar.getMaxTeam());
        seminarVO.setIntroduction(seminar.getIntroduction());
        seminarVO.setSeminarName(seminar.getSeminarName());
        seminarVO.setSeminarId(seminarId);
        seminarVO.setSeminarSerial(seminar.getSeminarSerial());
        return seminarVO;
    }


    /**
     * @author hzm
     * 修改班级讨论课状态
     * @param seminarId
     * @param klassMap
     * @return
     */
    public int updateKlassSeminarStatus(BigInteger seminarId,Map<String,Object> klassMap){
        SeminarVO seminarVO=new SeminarVO();
        seminarVO.setSeminarId(seminarId);
        seminarVO.setKlassId(new BigInteger(klassMap.get("klassId").toString()));
        seminarVO.setStatus(new Integer(klassMap.get("status").toString()));
        return seminarDao.updateKlassSeminarStatus(seminarVO);
    }

}
