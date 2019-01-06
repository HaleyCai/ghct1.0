package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author gfj
 */
public class QuestionListVO implements Serializable{
    private BigInteger questionId;
    private int klassSerial;
    private int teamSerial;
    private BigInteger studentId;
    private String studentName;
    private BigInteger attendanceId;
    private int selected;
    private double questionScore;

    public BigInteger getQuestionId() { return questionId; }

    public void setQuestionId(BigInteger questionId) { this.questionId = questionId; }

    public int getKlassSerial() { return klassSerial; }

    public void setKlassSerial(int klassSerial) { this.klassSerial = klassSerial; }

    public int getTeamSerial() { return teamSerial; }

    public void setTeamSerial(int teamSerial) { this.teamSerial = teamSerial; }

    public BigInteger getStudentId() { return studentId; }

    public void setStudentId(BigInteger studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public BigInteger getAttendanceId() { return attendanceId; }

    public void setAttendanceId(BigInteger attendanceId) { this.attendanceId = attendanceId; }

    public int getSelected() { return selected; }

    public void setSelected(int selected) { this.selected= selected; }

    public double getQuestionScore(){return questionScore;}

    public void setQuestionScore(double questionScore){this.questionScore=questionScore;}


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