package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.Attendance;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.exception.NotFoundException;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PresentationService {

    @Autowired
    PresentationDao presentationDao;

    @Autowired
    SeminarDao seminarDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    SeminarService seminarService;

    public int updateAttendanceOrderByAttendanceId(BigInteger attendanceId, Map<String,Object> orderMap) throws org.apache.ibatis.javassist.NotFoundException {
        int teamOrder=new Integer(orderMap.get("teamOrder").toString());
        return presentationDao.updateAttendanceOrderByAttendanceId(attendanceId,teamOrder);
    }

    public int deleteAttendanceByAttendance(BigInteger attendanceId) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.deleteAttendanceByAttendance(attendanceId);
    }

    public int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.updateReportByAttendanceId(attendanceId,reportUrl,reportName);
    }

    public Attendance getAttendanceByAttendanceId(BigInteger attendanceId) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.getAttendanceByAttendanceId(attendanceId);
    }

    public  int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.updatePPTByAttendanceId(attendanceId,pptUrl,pptName);
    }

    public List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
    }

    public int updatePresentByAttendanceId(BigInteger attendanceId,int present) throws org.apache.ibatis.javassist.NotFoundException {
        return presentationDao.updatePresentByAttendanceId(attendanceId,present);
    }


    public int insertAttendance(BigInteger klassSeminarId,BigInteger teamId,Map<String,Object> attendanceMap) throws SQLException {
        Attendance attendance=new Attendance();
        attendance.setTeamId(teamId);
        attendance.setPresent(0);
        attendance.setKlassSeminarId(klassSeminarId);
        attendance.setTeamOrder(new Integer(attendanceMap.get("teamOrder").toString()));
        return presentationDao.insertAttendance(attendance);
    }

    public Map<String,Object> getTeamKlassSeminarInfoByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        List<Attendance> attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        boolean isAttendance=false;
        for(Attendance item:attendanceList){
            if(item.getTeamId().equals(teamId)) {
                isAttendance=true;
                break;
            }
        }
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarVO.getSeminarId());
        seminarVO.setIntroduction(seminar.getIntroduction());
        seminarVO.setSeminarName(seminar.getSeminarName());
        seminarVO.setEnrollStartTime(seminar.getEnrollStartTime());
        seminarVO.setEnrollEndTime(seminar.getEnrollEndTime());
        seminarVO.setRoundId(seminar.getRoundId());
        seminarVO.setRoundSerial(roundDao.getRoundSerialByRoundId(seminar.getRoundId()));
        seminarVO.setSeminarSerial(seminar.getSeminarSerial());
        Map<String,Object> map=new HashMap<>();
        map.put("seminarVO",seminarVO);
        if(isAttendance==false){   //未报名讨论课
            map.put("attendanceStatus",false);
            return map;
        }
        else{                     //已报名讨论课
            Attendance attendance=presentationDao.getAttendanceByKlassSeminarIdByTeamId(klassSeminarId,teamId);
            if(seminarVO.getStatus()==2){
                if(attendance.getReportName()!=null&&attendance.getReportName().length()>0){
                    String reportStatus ="已提交";
                    map.put("submitStatus",false);
                    map.put("reportStatus",reportStatus);
                }else{
                    Date reportDDL=seminarVO.getReportDDL();
                    Date nowDate=new Date();
                    long diff=reportDDL.getTime()-nowDate.getTime();
                    if(diff>0) {
                        long hours = diff / (1000 * 60 * 60);
                        long minutes = diff % (1000 * 60 * 60) / (1000 * 60);
                        String reportStatus ="未提交  距截止时间为"+hours+"时"+minutes+"分";
                        map.put("submitStatus",true);
                        map.put("reportStatus",reportStatus);
                    }
                    else{
                        String reportStatus ="未提交";
                        map.put("submitStatus",false);
                        map.put("reportStatus",reportStatus);
                    }
                }
            }
            Klass klass=klassDao.getKlassByKlassId(seminarVO.getKlassId());
            seminarVO.setKlassSerial(klass.getKlassSerial());
            map.put("grade",klass.getGrade());
            int teamSerial=teamDao.getTeamSerialByTeamId(teamId);
            map.put("teamId",teamId);
            map.put("teamSerial",teamSerial);
            if(attendance.getPptName()!=null&&attendance.getPptName().length()>0){
                map.put("pptStatus",true);
            }
            else map.put("pptStatus",false);
            map.put("attendanceStatus",true);
            map.put("attendanceId",attendance.getAttendanceId());
            return map;
        }
    }

    public List<Map> modifyAttendanceByAttendanceId(BigInteger attendanceId,Map<String,String> orderMap) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        int teamOrder=new Integer(orderMap.get("teamOrder"));
        presentationDao.updateAttendanceOrderByAttendanceId(attendanceId,teamOrder);
        Map<String,Object> flagMap=new HashMap<>();
        Attendance attendance=presentationDao.getAttendanceByAttendanceId(attendanceId);
        List<Map> map=seminarService.listStudentKlassSeminarByKlassSeminarId(attendance.getKlassSeminarId());
        map.add(flagMap);
        return map;
    }

    /**
     * 获取最大teamOrder
     * @param klassSeminarId
     * @return
     */
    public int selectMaxTeamOrderByKlassSeminarId(BigInteger klassSeminarId){
        return presentationDao.selectMaxTeamOrderByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getPresentTeam(int present){
        return presentationDao.getPresentTeam(present);
    }

    public BigInteger getAttendanceIdByTeamOrder(Integer teamOrder){
        return presentationDao.getAttendanceIdByTeamOrder(teamOrder);
    }
}
