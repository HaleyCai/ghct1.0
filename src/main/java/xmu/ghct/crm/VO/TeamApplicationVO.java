package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

//发送的非法组队请求
public class TeamApplicationVO implements Serializable {
    private BigInteger teamValidId;
    private BigInteger courseId;
    private String courseName;
    private int klassSerial;
    private BigInteger teamId;
    private int teamSerial;
    private BigInteger teacherId;
    private String reason;
    private int status;

    public BigInteger getTeamValidId() {
        return teamValidId;
    }

    public void setTeamValidId(BigInteger teamValidId) {
        this.teamValidId = teamValidId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public int getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(int teamSerial) {
        this.teamSerial = teamSerial;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeamApplicationVO{" +
                "teamValidId=" + teamValidId +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", klassSerial=" + klassSerial +
                ", teamId=" + teamId +
                ", teamSerial=" + teamSerial +
                ", teacherId=" + teacherId +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}