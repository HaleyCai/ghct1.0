package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 学生界面，课程显示的信息
 */
public class KlassInfoVO implements Serializable {
    private BigInteger courseId;
    private String courseName;
    private BigInteger klassId;
    private BigInteger klassName;

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

    public BigInteger getKlassName() {
        return klassName;
    }

    public void setKlassName(BigInteger klassName) {
        this.klassName = klassName;
    }

    @Override
    public String toString() {
        return "KlassInfoVO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", klassId=" + klassId +
                ", klassName=" + klassName +
                '}';
    }
}
