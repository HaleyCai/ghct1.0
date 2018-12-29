package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class TeamInfoVO implements Serializable {

    private BigInteger teamId;
    private String teamName;
    private BigInteger klassId;
    private int klassSerial;
    private int teamSerial;
    private int status;
    private StudentVO teamLeader;
    private List<StudentVO> members;

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

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public int getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(int teamSerial) {
        this.teamSerial = teamSerial;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StudentVO getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(StudentVO teamLeader) {
        this.teamLeader = teamLeader;
    }

    public List<StudentVO> getMembers() {
        return members;
    }

    public void setMembers(List<StudentVO> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "TeamInfoVO{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", klassId=" + klassId +
                ", klassSerial=" + klassSerial +
                ", teamSerial=" + teamSerial +
                ", status=" + status +
                ", teamLeader=" + teamLeader +
                ", members=" + members +
                '}';
    }
}
