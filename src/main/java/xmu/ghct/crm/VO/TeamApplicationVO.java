package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class TeamApplicationVO implements Serializable {
    private BigInteger id;
    private BigInteger teamId;
    private BigInteger teacherId;
    private String reason;
    private int status;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
                "id=" + id +
                ", teamId='" + teamId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", reason=" + reason +
                ", status=" + status +
                '}';
    }

}
