package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class CourseLimitVO {

    BigInteger courseLimitId;
    BigInteger courseId;
    String courseName;
    private int minMember;
    private int maxMember;

    public void setCourseLimitId(BigInteger courseLimitId) {
        this.courseLimitId = courseLimitId;
    }

    public BigInteger getCourseLimitId() {
        return courseLimitId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setMinMember(int minMember) {
        this.minMember = minMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMinMember() {
        return minMember;
    }

    public int getMaxMember() {
        return maxMember;
    }

    @Override
    public String toString() {
        return "CourseLimitVO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
