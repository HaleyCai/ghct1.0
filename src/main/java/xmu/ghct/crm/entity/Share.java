package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Share implements Serializable {
    private String shareType;
    private BigInteger mainCourseId;
    private BigInteger subCourseId;
    private BigInteger subCourseTeacherId;
    private Boolean status;


    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public BigInteger getMainCourseId() { return mainCourseId; }

    public void setMainCourseId(BigInteger mainCourseId) { this.mainCourseId = mainCourseId; }

    public BigInteger getSubCourseId() { return subCourseId; }

    public void setSubCourseId(BigInteger subCourseId) { this.subCourseId = subCourseId; }

    public BigInteger getSubCourseTeacherId() { return subCourseTeacherId; }

    public void setSubCourseTeacherId(BigInteger subCourseTeacherId) { this.subCourseTeacherId = subCourseTeacherId; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }



    @Override
    public String toString() {
        return "Share{" +
                "shareType=" + shareType +
                ", mainCourseId='" + mainCourseId + '\'' +
                ", subCourseId='" + subCourseId + '\'' +
                ", subCourseTeacherId=" + subCourseTeacherId +
                ", status=" + status +
                '}';
    }
}