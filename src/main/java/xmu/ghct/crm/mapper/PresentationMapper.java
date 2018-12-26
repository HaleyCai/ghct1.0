package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Attendance;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface PresentationMapper {

    /**
     * @author hzm
     * 根据attendanceId修改讨论课报名顺序
     * @param attendanceId
     * @param teamOrder
     * @return
     */
    int updateAttendanceOrderByAttendanceId(BigInteger attendanceId,int teamOrder);

    /**
     * @author hzm
     * 根据attendanceId取消报名讨论课
     * @param attendanceId
     * @return
     */
    int deleteAttendanceByAttendance(BigInteger attendanceId);


    /**
     * @author hzm
     * 根据attendanceId更新报告信息
     * @param attendanceId
     * @return
     */
    int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName);


    /**
     * @author hzm
     * 根据attendanceId更新ppt信息
     * @param attendanceId
     * @param pptUrl
     * @param pptName
     * @return
     */
    int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName);

    /**
     * @author hzm
     * 根据attendanceId获取讨论课报名信息
     * @param attendanceId
     * @return
     */
    Attendance getAttendanceByAttendanceId(BigInteger attendanceId);


    /**
     * @author hzm
     * 根据klassSeminarId
     * @param klassSeminarId
     * @return
     */
    List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId);
}
