package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */
public class TeamInfoVO implements Serializable {

    private BigInteger teamId;
    private String teamName;
    private BigInteger klassId;
    private Integer klassSerial;
    private Integer teamSerial;
    private Integer status;
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

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public Integer getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(Integer teamSerial) {
        this.teamSerial = teamSerial;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
