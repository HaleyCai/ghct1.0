package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class TeamSimpleInfo implements Serializable {
    private BigInteger teamId;
    private String teamName;
    String teamSerial;
    int status;

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(String teamSerial) {
        this.teamSerial = teamSerial;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeamSimpleInfo{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamSerial=" + teamSerial +
                ", status=" + status +
                '}';
    }
}
