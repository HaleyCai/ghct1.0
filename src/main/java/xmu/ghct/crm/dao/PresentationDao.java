package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Attendance;
import xmu.ghct.crm.mapper.PresentationMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class PresentationDao {

    @Autowired
    PresentationMapper presentationMapper;

    public int updateAttendanceOrderByAttendanceId(BigInteger attendanceId,int teamOrder){
        return presentationMapper.updateAttendanceOrderByAttendanceId(attendanceId,teamOrder);
    }

    public int deleteAttendanceByAttendance(BigInteger attendanceId){
        return presentationMapper.deleteAttendanceByAttendance(attendanceId);
    }

    public int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName){
        return presentationMapper.updateReportByAttendanceId(attendanceId,reportUrl,reportName);
    }

    public Attendance getAttendanceByAttendanceId(BigInteger attendanceId){
        return presentationMapper.getAttendanceByAttendanceId(attendanceId);
    }

    public  int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName){
        return presentationMapper.updatePPTByAttendanceId(attendanceId,pptUrl,pptName);
    }

    public List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId){
        return presentationMapper.listAttendanceByKlassSeminarId(klassSeminarId);
    }

    public int updatePresentByAttendanceId(BigInteger attendanceId,int present){
        return presentationMapper.updatePresentByAttendanceId(attendanceId,present);
    }


    public int insertAttendance(Attendance attendance){
        return presentationMapper.insertAttendance(attendance);
    }

    public Attendance getAttendanceByKlassSeminarIdByTeamId(BigInteger klassSeminarId,BigInteger teamId){
        return presentationMapper.getAttendanceByKlassSeminarIdByTeamId(klassSeminarId,teamId);
    }
}
