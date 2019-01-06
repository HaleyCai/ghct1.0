package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author gfj
 */
public class QuestionVO implements Serializable{
    private BigInteger questionId;
    private BigInteger klassSeminarId;
    private BigInteger attendanceId;
    private BigInteger teamId;
    private String teamName;
    private BigInteger studentId;
    private BigInteger seminarId;
    private BigInteger klassId;
    private Integer selected;
    private Double questionScore;

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(BigInteger attendanceId) {
        this.attendanceId = attendanceId;
    }

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

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", klassSeminarId='" + klassSeminarId + '\'' +
                ", attendanceId='" + attendanceId + '\'' +
                ", teamId=" + teamId +
                ", teamName=" + teamName +
                ", studentId=" + studentId +
                ", seminarId=" + seminarId +
                ", klassId=" + klassId +
                ", selected=" + selected +
                ", questionScore=" + questionScore +
                '}';
    }
}
