package xmu.ghct.crm.service;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.SeminarDao;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class SeminarService {
    @Autowired
    SeminarDao seminarDao;

    public int creatSeminar(Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        seminar.setSeminarId(new BigInteger(seminarMap.get("seminarId").toString()));
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setVisible(new Boolean(seminarMap.get("visible").toString()));
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
        Date start = formatter.parse(seminarMap.get("enrollStartTime").toString()+" 00:00:00");
        seminar.setEnrollStartTime(start);
        Date end = formatter.parse(seminarMap.get("enrollEndTime").toString()+" 00:00:00");
        seminar.setEnrollEndTime(end);
        return seminarDao.creatSeminar(seminar);
    }


}
