package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class QuestionListVO implements Serializable{
    private BigInteger questionId;
    private int klassSerial;
    private int teamSerial;
    private String studentName;
    private BigInteger attendanceId;
    private int selected;

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public int getKlassSerial() { return klassSerial; }

    public void setKlassSerial(int klassSerial) { this.klassSerial = klassSerial; }

    public int getTeamSerial() { return teamSerial; }

    public void setTeamSerial(int teamSerial) {
        this.teamSerial = teamSerial;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public BigInteger getAttendanceId() { return attendanceId; }

    public void setAttendanceId(BigInteger attendanceId) { this.attendanceId = attendanceId; }

    public int getSelected() { return selected; }

    public void setSelected(int selected) {
        this.selected= selected;
    }


    @Override
    public String toString() {
        return "QuestionListVO{" +
                "questionId=" + questionId +
                ", klassSerial=" + klassSerial + '\'' +
                ", teamSerial=" + teamSerial + '\'' +
                ", studentName=" + studentName + '\'' +
                ", attendanceId=" + attendanceId + '\'' +
                ", selected=" + selected +
                '}';
    }
}