package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author caiyq
 */
public class ShareRequestVO {
    private BigInteger shareId;
    private Integer shareType;
    private BigInteger mainCourseId;
    private String mainCourseName;
    private String mainTeacherName;
    private BigInteger subCourseId;
    private String subCourseName;
    private BigInteger subTeacherId;
    private Integer status;

    public BigInteger getShareId() {
        return shareId;
    }

    public void setShareId(BigInteger shareId) {
        this.shareId = shareId;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public BigInteger getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(BigInteger mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public String getMainCourseName() {
        return mainCourseName;
    }

    public void setMainCourseName(String mainCourseName) {
        this.mainCourseName = mainCourseName;
    }

    public String getMainTeacherName() {
        return mainTeacherName;
    }

    public void setMainTeacherName(String mainTeacherName) {
        this.mainTeacherName = mainTeacherName;
    }

    public BigInteger getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(BigInteger subCourseId) {
        this.subCourseId = subCourseId;
    }

    public String getSubCourseName() {
        return subCourseName;
    }

    public void setSubCourseName(String subCourseName) {
        this.subCourseName = subCourseName;
    }

    public BigInteger getSubTeacherId() {
        return subTeacherId;
    }

    public void setSubTeacherId(BigInteger subTeacherId) {
        this.subTeacherId = subTeacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShareRequestVO{" +
                "shareId=" + shareId +
                ", shareType='" + shareType + '\'' +
                ", mainCourseId=" + mainCourseId +
                ", mainCourseName='" + mainCourseName + '\'' +
                ", mainTeacherName='" + mainTeacherName + '\'' +
                ", subCourseId=" + subCourseId +
                ", subCourseName='" + subCourseName + '\'' +
                ", subTeacherId=" + subTeacherId +
                ", status=" + status +
                '}';
    }
}
