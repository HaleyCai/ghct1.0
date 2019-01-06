package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class CourseWithTeacherVO {
    private BigInteger courseId;
    private String courseNaem;
    private BigInteger teacherId;
    private String teacherName;

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public String getCourseNaem() {
        return courseNaem;
    }

    public void setCourseNaem(String courseNaem) {
        this.courseNaem = courseNaem;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "CourseWithTeacherVO{" +
                "courseId=" + courseId +
                ", courseNaem='" + courseNaem + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
