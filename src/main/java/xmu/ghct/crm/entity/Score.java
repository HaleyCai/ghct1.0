package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Score implements Serializable {

    private BigInteger klassSeminarId;
    private String seminarName;
    private  BigInteger teamId;
    private double presentationScore;
    private double questionScore;
    private double reportScore;
    private double totalScore;

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public void setPresentationScore(double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public void setQuestionScore(double questionScore) {
        this.questionScore = questionScore;
    }

    public void setReportScore(double reportScore) {
        this.reportScore = reportScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }


    public double getPresentationScore() {
        return presentationScore;
    }

    public double getQuestionScore() {
        return questionScore;
    }

    public double getReportScore() {
        return reportScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getSeminarName() {
        return seminarName;
    }

    @Override
    public String toString() {
        return "Score{" +
                "klassSeminarId=" + klassSeminarId +
                ", seminarName=" + seminarName +
                ", teamId=" + teamId +
                ", presentationScore=" + presentationScore +
                ", questionScore=" + questionScore +
                ", reportScore=" + reportScore +
                ", totalScore=" + totalScore +
                '}';
    }


}
