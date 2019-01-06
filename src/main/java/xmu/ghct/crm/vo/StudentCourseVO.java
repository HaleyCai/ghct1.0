package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class StudentCourseVO {

    BigInteger courseId;
    String courseName;
    BigInteger klassId;
    int  klassSerial;
    int grade;

    public BigInteger getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public int getGrade() {
        return grade;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    @Override
    public String toString() {
        return "StudentCourseVO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", klassId=" + klassId +
                ", klassSerial=" + klassSerial +
                ", grade=" + grade +
                '}';
    }

}
