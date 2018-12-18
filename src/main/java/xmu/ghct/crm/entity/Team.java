package xmu.ghct.crm.entity;

import java.math.BigInteger;

public class Team {

    BigInteger teamId;
    BigInteger klassId;
    BigInteger courseId;
    BigInteger leaderId;
    String teamName;
    Double teamSerial;
    int status;

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

    public Double getTeamSerial() {
        return teamSerial;
    }

    public int getStatus() {
        return status;
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

    public void setTeamSerial(Double teamSerial) {
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
                ", status=" + status +
                '}';
    }

}
