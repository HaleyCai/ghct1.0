package xmu.ghct.crm.VO;

import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;

public class ScoreVO {

    private BigInteger roundId;
    private  BigInteger teamId;
    private double presentationScore;
    private double questionScore;
    private double reportScore;
    private double totalScore;
    private List<Score> scoreList;

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public BigInteger getTeamId() {
        return teamId;
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

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
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

    @Override
    public String toString() {
        return "ScoreVO{" +
                "roundId=" + roundId +
                ", teamId=" + teamId +
                ", presentationScore=" + presentationScore +
                ", questionScore=" + questionScore +
                ", reportScore=" + reportScore +
                ", totalScore=" + totalScore +
                ", scoreList=" + scoreList +
                '}';
    }

}
