package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author caiyq
 */
public class CourseStudentVO {
    private BigInteger studentId;
    private BigInteger courseId;
    private String courseName;
    private Integer grade;
    private BigInteger klassId;
    private Integer klassSerial;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
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
