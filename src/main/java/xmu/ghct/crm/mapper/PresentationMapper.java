package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Attendance;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
@Mapper
@Repository
public interface PresentationMapper {

    /**
     * 根据attendanceId修改讨论课报名顺序
     * @param attendanceId
     * @param teamOrder
     * @return
     */
    int updateAttendanceOrderByAttendanceId(BigInteger attendanceId,int teamOrder);

    /**
     * 根据attendanceId取消报名讨论课
     * @param attendanceId
     * @return
     */
    int deleteAttendanceByAttendance(BigInteger attendanceId);


    /**
     * 根据attendanceId更新报告信息
     * @param attendanceId
     * @param reportName
     * @param reportUrl
     * @return
     */
    int updateReportByAttendanceId(BigInteger attendanceId,String reportUrl,String reportName);


    /**
     * 根据attendanceId更新ppt信息
     * @param attendanceId
     * @param pptUrl
     * @param pptName
     * @return
     */
    int updatePPTByAttendanceId(BigInteger attendanceId,String pptUrl,String pptName);

    /**
     * 根据attendanceId获取讨论课报名信息
     * @param attendanceId
     * @return
     */
    Attendance getAttendanceByAttendanceId(BigInteger attendanceId);


    /**
     * 根据klassSeminarId
     * @param klassSeminarId
     * @return
     */
    List<Attendance> listAttendanceByKlassSeminarId(BigInteger klassSeminarId);

    /**
     * 根据attendanceId修改进行状态
     * @param attendanceId
     * @param present
     * @return
     */
    int updatePresentByAttendanceId(BigInteger attendanceId,int present);

    /**
     * 创建报名信息
     * @param attendance
     * @return
     */
    int insertAttendance(Attendance attendance);


    /**
     * 根据klassSeminarId和teamId获取某小组报名信息
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    Attendance getAttendanceByKlassSeminarIdByTeamId(BigInteger klassSeminarId,BigInteger teamId);

    /**
     * 获取最大teamOrder
     * @param klassSeminarId
     * @return
     */
    Integer selectMaxTeamOrderByKlassSeminarId(BigInteger klassSeminarId);


    /**
     * 获取正在展示小组ID
     * @param present
     * @return
     */
    BigInteger getPresentTeam(Integer present);

    /**
     * 根据报名序号获取队伍
     * @param teamOrder
     * @return
     */
    BigInteger getAttendanceIdByTeamOrder(Integer teamOrder);
}
