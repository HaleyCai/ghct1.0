package xmu.ghct.crm.entity;

import java.math.BigInteger;

public class Team {

    private BigInteger teamId;
    private BigInteger klassId;
    private BigInteger courseId;
    private BigInteger leaderId;
    private String teamName;
    private int teamSerial;
    private int klassSerial;
    private int status;

    public BigInteger getTeamId() {
        return teamId;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public BigInteger getLeaderId() {
        return leaderId;
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

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setLeaderId(BigInteger leaderId) {
        this.leaderId = leaderId;
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

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", klassId=" + klassId +
                ", courseId=" + courseId +
                ", leaderId=" + leaderId +
                ", teamName='" + teamName + '\'' +
                ", teamSerial=" + teamSerial +
                ", klassSerial=" + klassSerial +
                ", status=" + status +
                '}';
    }

}
