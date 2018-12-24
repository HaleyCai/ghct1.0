package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class TeamInfoVO implements Serializable {

    private BigInteger teamId;
    private String teamName;
    int teamSerial;
    int status;
   StudentVO teamLeader;
    List<StudentVO> members;

    public BigInteger getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamSerial() {
        return teamSerial;
    }

    public int getStatus() {
        return status;
    }

    public StudentVO getTeamLeader() {
        return teamLeader;
    }

    public List<StudentVO> getMembers() {
        return members;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamSerial(int teamSerial) {
        this.teamSerial = teamSerial;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTeamLeader(StudentVO teamLeader) {
        this.teamLeader = teamLeader;
    }

    public void setMembers(List<StudentVO> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "TeamInfoVO{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamSerial=" + teamSerial +
                ", status=" + status +
                ", teamLeader=" + teamLeader +
                ", members=" + members +
                '}';
    }
}
