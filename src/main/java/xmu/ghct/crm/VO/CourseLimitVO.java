package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class CourseLimitVO {

    BigInteger courseLimitId;
    BigInteger courseId;
    String CourseName;
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
        CourseName = courseName;
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
        return CourseName;
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
                ", CourseName='" + CourseName + '\'' +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
