package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author gfj
 */
public class CourseTeacherVO {
    private BigInteger courseId;
    private String courseName;
    private BigInteger klassSeminarId;

    @Override
    public String toString() {
        return "CourseTeacherVO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", klassSeminarId=" + klassSeminarId +
                '}';
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
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

}
