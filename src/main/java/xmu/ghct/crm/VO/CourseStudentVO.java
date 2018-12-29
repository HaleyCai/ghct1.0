package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class CourseStudentVO {
    private BigInteger studentId;
    private BigInteger courseId;
    private String courseName;
    private int grade;
    private BigInteger klassId;
    private int klassSerial;

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    @Override
    public String toString() {
        return "CourseStudentVO{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", grade=" + grade +
                ", klassId=" + klassId +
                ", klassSerial=" + klassSerial +
                '}';
    }
}
