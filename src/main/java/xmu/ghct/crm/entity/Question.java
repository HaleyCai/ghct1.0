package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Question implements Serializable{
    private BigInteger questionId;
    private BigInteger klassSeminarId;
    private BigInteger attendanceId;
    private BigInteger teamId;
    private BigInteger studentId;
    private int selected;
    private double questionScore;

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

    public BigInteger getAttendanceId() { return attendanceId; }

    public void setAttendanceId(BigInteger attendanceId) { this.attendanceId = attendanceId; }

    public BigInteger getTeamId() { return teamId; }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) { this.studentId = studentId; }

    public int getSelected() { return selected; }

    public void setSelected(int selected) {
        this.selected= selected;
    }

    public double getQuestionScore() { return questionScore; }

    public void setQuestionScore(Double questionScore) { this.questionScore = questionScore; }


    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", klassSeminarId='" + klassSeminarId + '\'' +
                ", attendanceId='" + attendanceId + '\'' +
                ", teamId=" + teamId +
                ", studentId=" + studentId +
                ", selected=" + selected +
                ", questionScore=" + questionScore +
                '}';
    }
}
