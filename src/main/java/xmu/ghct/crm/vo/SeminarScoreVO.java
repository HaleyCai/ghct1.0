package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class SeminarScoreVO {

    private BigInteger klassSeminarId;
    private BigInteger seminarId;
    private BigInteger teamId;
    private String teamName;
    private String seminarName;
    private int seminarSerial;
    private String introduction;
    private int grade;
    private int klassSerial;
    private double teamSerial;
    private double presentationScore;
    private double questionScore;
    private double reportScore;
    private double totalScore;

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public void setSeminarSerial(int seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public void setTeamSerial(double teamSerial) {
        this.teamSerial = teamSerial;
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

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public int getSeminarSerial() {
        return seminarSerial;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getGrade() {
        return grade;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public double getTeamSerial() {
        return teamSerial;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "SeminarScoreVO{" +
                "klassSeminarId=" + klassSeminarId +
                ", seminarId=" + seminarId +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", seminarName='" + seminarName + '\'' +
                ", seminarSerial=" + seminarSerial +
                ", introduction='" + introduction + '\'' +
                ", grade=" + grade +
                ", klassSerial=" + klassSerial +
                ", teamSerial=" + teamSerial +
                ", presentationScore=" + presentationScore +
                ", questionScore=" + questionScore +
                ", reportScore=" + reportScore +
                ", totalScore=" + totalScore +
                '}';
    }

}
