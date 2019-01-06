package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author gfj
 */
public class QuestionListVO implements Serializable{
    private BigInteger questionId;
    private Integer klassSerial;
    private Integer teamSerial;
    private BigInteger studentId;
    private String studentName;
    private BigInteger attendanceId;
    private Integer selected;
    private Double questionScore;

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
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

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public BigInteger getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(BigInteger attendanceId) {
        this.attendanceId = attendanceId;
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
        return "QuestionListVO{" +
                "questionId=" + questionId +
                ", klassSerial=" + klassSerial + '\'' +
                ", teamSerial=" + teamSerial + '\'' +
                ", studentId=" + studentId + '\'' +
                ", studentName=" + studentName + '\'' +
                ", attendanceId=" + attendanceId + '\'' +
                ", selected=" + selected + '\'' +
                ", questionScore=" + questionScore +
                '}';
    }
}