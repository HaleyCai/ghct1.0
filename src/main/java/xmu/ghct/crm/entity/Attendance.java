package xmu.ghct.crm.entity;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class Attendance {

    BigInteger attendanceId;
    BigInteger klassSeminarId;
    BigInteger teamId;
    int teamOrder;
    int present;
    String reportName;
    String reportUrl;
    String pptName;
    String pptUrl;


    public BigInteger getAttendanceId() {
        return attendanceId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public int getTeamOrder() {
        return teamOrder;
    }

    public int getPresent() {
        return present;
    }

    public String getReportName() {
        return reportName;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public String getPptName() {
        return pptName;
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setAttendanceId(BigInteger attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public void setTeamOrder(int teamOrder) {
        this.teamOrder = teamOrder;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }


    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", klassSeminarId=" + klassSeminarId +
                ", teamId=" + teamId +
                ", teamOrder=" + teamOrder +
                ", present=" + present +
                ", reportName='" + reportName + '\'' +
                ", reportUrl='" + reportUrl + '\'' +
                ", pptName='" + pptName + '\'' +
                ", pptUrl='" + pptUrl + '\'' +
                '}';
    }
}
