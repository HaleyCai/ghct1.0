package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author caiyq
 */
public class StudentCourseVO {

    private BigInteger courseId;
    private String courseName;
    private BigInteger klassId;
    private Integer  klassSerial;
    private Integer grade;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
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
