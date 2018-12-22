package xmu.ghct.crm.service;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.ghct.crm.VO.SeminarSimpleVO;
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

    public int creatSeminar(Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        seminar.setSeminarId(new BigInteger(seminarMap.get("seminarId").toString()));
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        if(seminarMap.get("roundId").toString()==null){
            Round round=new Round();
            round.setRoundId(new BigInteger("1"));
            round.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
            round.setPresentationScoreMethod(1);
            round.setQuestionScoreMethod(0);
            round.setReportScoreMethod(1);
            round.setRoundSerial(0);
            roundDao.insertRound(round);
            seminar.setRoundId(new BigInteger("1"));
        }else{
            seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        }
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date start = formatter.parse(seminarMap.get("enrollStartTime").toString()+" 00:00:00");
        seminar.setEnrollStartTime(start);
        Date end = formatter.parse(seminarMap.get("enrollEndTime").toString()+" 00:00:00");
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
     * 根据seminarId获得其所属班级信息
     * @param seminarId
     * @return
     */
    public List<Klass> listKlassBySeminarId(BigInteger seminarId){
        List<BigInteger> klassIdList=seminarDao.listKlassIdBySeminarId(seminarId);
        List<Klass> klassList=new ArrayList<>();
        for(BigInteger item:klassIdList){
            Klass klass =klassDao.getKlassByKlassId(item);
            klassList.add(klass);
        }
        return klassList;
    }

    /**
     * 根据seminarId修改讨论课信息
     * @param seminarId
     * @return
     */
    public int updateSeminarBySeminarId(BigInteger seminarId,@RequestBody Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date enrollStartTime = formatter.parse(seminarMap.get("enrollStartTime").toString()+" 00:00:00");
        seminar.setEnrollStartTime(enrollStartTime);
        Date enrollEndTime = formatter.parse(seminarMap.get("enrollEndTime").toString()+" 00:00:00");
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

}
