package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class TeamApplicationVO implements Serializable {
    private BigInteger teamValidId;
    private BigInteger teamId;
    private BigInteger teacherId;
    private String reason;
    private int status;

    public BigInteger getTeamValidId() {
        return teamValidId;
    }

    public void setTeamValidId(BigInteger teamValidId) {
        this.teamValidId = teamValidId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }


    @Override
    public String toString() {
        return "TeamApplicationVO{" +
                "teamValidId=" + teamValidId +
                ", teamId='" + teamId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", reason=" + reason +
                ", status=" + status +
                '}';
    }

}