package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class ShareVO {
    private BigInteger shareId;
    private String shareType;//共享的类型是什么
    private BigInteger courseId;
    private String courseName;
    private String courseType;//课程是主还是从
    private String otherTeacherName;//共享的另一位教师名
    private int status;

    public BigInteger getShareId() {
        return shareId;
    }

    public void setShareId(BigInteger shareId) {
        this.shareId = shareId;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getOtherTeacherName() {
        return otherTeacherName;
    }

    public void setOtherTeacherName(String otherTeacherName) {
        this.otherTeacherName = otherTeacherName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShareVO{" +
                "shareId=" + shareId +
                ", shareType='" + shareType + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseType='" + courseType + '\'' +
                ", otherTeacherName='" + otherTeacherName + '\'' +
                ", status=" + status +
                '}';
    }
}
