package xmu.ghct.crm.dao;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Attendance;
import xmu.ghct.crm.mapper.PresentationMapper;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

@Component
public class PresentationDao {

    @Autowired
    PresentationMapper presentationMapper;

    public int updateAttendanceOrderByAttendanceId(BigInteger attendanceId,int teamOrder) throws NotFoundException {
        int count=presentationMapper.updateAttendanceOrderByAttendanceId(attendanceId,teamOrder);
        if(count<=0)
        {
            throw new NotFoundException("未找到该展示");
        }
        return count;
    }

    public int deleteAttendanceByAttendance(BigInteger attendanceId) throws NotFoundException {
        int count=presentationMapper.deleteAttendanceByAttendance(attendanceId);
        if(count<=0)
        {
            throw new NotFoundException("未找到该展示");
        }
        return count;
    }

    public int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName) throws NotFoundException {
        int count=presentationMapper.updateReportByAttendanceId(attendanceId,reportUrl,reportName);
        if(count<=0)
        {
            throw new NotFoundException("未找到该展示");
        }
        return count;
    }

    public Attendance getAttendanceByAttendanceId(BigInteger attendanceId) throws NotFoundException {
        Attendance attendance=presentationMapper.getAttendanceByAttendanceId(attendanceId);
        if(attendance==null)
        {
            throw new NotFoundException("未找到该展示");
        }
        return attendance;
    }

    public  int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName) throws NotFoundException {
        int count=presentationMapper.updatePPTByAttendanceId(attendanceId,pptUrl,pptName);
        if(count<=0)
        {
            throw new NotFoundException("未找到该展示");
        }
        return count;
    }

    public List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        List<Attendance> attendances=presentationMapper.listAttendanceByKlassSeminarId(klassSeminarId);
        if(attendances==null&&attendances.isEmpty())
        {
            throw new NotFoundException("未找到该展示");
        }
        return attendances;
    }

    public int updatePresentByAttendanceId(BigInteger attendanceId,int present) throws NotFoundException {
        int count=presentationMapper.updatePresentByAttendanceId(attendanceId,present);
        if(count<=0)
        {
            throw new NotFoundException("未找到该展示");
        }
        return count;
    }


    public int insertAttendance(Attendance attendance) throws SQLException {
        Attendance attendance1=presentationMapper.getAttendanceByKlassSeminarIdByTeamId(attendance.getKlassSeminarId(),
                                                                    attendance.getTeamId());
        if(attendance1!=null)
        {
            throw new SQLException("该展示已存在");
        }
        return presentationMapper.insertAttendance(attendance);
    }

    public Attendance getAttendanceByKlassSeminarIdByTeamId(BigInteger klassSeminarId,BigInteger teamId) throws NotFoundException {
        Attendance attendance=presentationMapper.getAttendanceByKlassSeminarIdByTeamId(klassSeminarId,teamId);
        if(attendance==null)
        {
            throw new NotFoundException("未找到该展示");
        }
        return attendance;
    }

    /**
     * 获取最大teamOrder
     * @param klassSeminarId
     * @return
     */
     public int selectMaxTeamOrderByKlassSeminarId(BigInteger klassSeminarId){
        return presentationMapper.selectMaxTeamOrderByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getPresentTeam(int present){
         return presentationMapper.getPresentTeam(present);
    }

    public BigInteger getAttendanceIdByTeamOrder(Integer teamOrder){
         return presentationMapper.getAttendanceIdByTeamOrder(teamOrder);
    }
}
