package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author gfj
 */
public class TeamApplicationVO implements Serializable {
    private BigInteger teamValidId;
    private Integer klassSerial;
    private BigInteger teamId;
    private Integer teamSerial;
    private BigInteger teacherId;
    private String reason;
    private Integer status;

    public BigInteger getTeamValidId() {
        return teamValidId;
    }

    public void setTeamValidId(BigInteger teamValidId) {
        this.teamValidId = teamValidId;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(Integer teamSerial) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeamApplicationVO{" +
                "teamValidId=" + teamValidId +
                ", klassSerial=" + klassSerial +
                ", teamId=" + teamId +
                ", teamSerial=" + teamSerial +
                ", teacherId=" + teacherId +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}