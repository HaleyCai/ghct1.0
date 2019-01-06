package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author caiyq
 */
public class ShareVO {
    private BigInteger shareId;
    /**
     * 共享的类型是什么，1组队，2讨论课
     */
    private String shareType;
    private BigInteger otherCourseId;
    private String otherCourseName;
    /**
     * 共享的另一位教师名
     */
    private String otherTeacherName;
    /**
     * 课程是主还是从
     */
    private String myCourseType;
    private Integer status;

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

    public BigInteger getOtherCourseId() {
        return otherCourseId;
    }

    public void setOtherCourseId(BigInteger otherCourseId) {
        this.otherCourseId = otherCourseId;
    }

    public String getOtherCourseName() {
        return otherCourseName;
    }

    public void setOtherCourseName(String otherCourseName) {
        this.otherCourseName = otherCourseName;
    }

    public String getOtherTeacherName() {
        return otherTeacherName;
    }

    public void setOtherTeacherName(String otherTeacherName) {
        this.otherTeacherName = otherTeacherName;
    }

    public String getMyCourseType() {
        return myCourseType;
    }

    public void setMyCourseType(String myCourseType) {
        this.myCourseType = myCourseType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShareVO{" +
                "shareId=" + shareId +
                ", shareType='" + shareType + '\'' +
                ", otherCourseId=" + otherCourseId +
                ", otherCourseName='" + otherCourseName + '\'' +
                ", otherTeacherName='" + otherTeacherName + '\'' +
                ", myCourseType='" + myCourseType + '\'' +
                ", status=" + status +
                '}';
    }
}
