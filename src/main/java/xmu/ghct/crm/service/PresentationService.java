package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.PresentationDao;
import xmu.ghct.crm.entity.Attendance;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class PresentationService {

    @Autowired
    PresentationDao presentationDao;

    public int updateAttendanceOrderByAttendanceId(BigInteger attendanceId, Map<String,Object> orderMap){
        int teamOrder=new Integer(orderMap.get("teamOrder").toString());
        return presentationDao.updateAttendanceOrderByAttendanceId(attendanceId,teamOrder);
    }

    public int deleteAttendanceByAttendance(BigInteger attendanceId) {
        return presentationDao.deleteAttendanceByAttendance(attendanceId);
    }

    public int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName){
        return presentationDao.updateReportByAttendanceId(attendanceId,reportUrl,reportName);
    }

    public Attendance getAttendanceByAttendanceId(BigInteger attendanceId){
        return presentationDao.getAttendanceByAttendanceId(attendanceId);
    }

    public  int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName){
        return presentationDao.updatePPTByAttendanceId(attendanceId,pptUrl,pptName);
    }

    public List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId){
        return presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
    }
}
