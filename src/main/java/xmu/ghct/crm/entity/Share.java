package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author caiyq
 */
public class Share implements Serializable {
    private BigInteger shareId;
    /**
     * 1.team,2.seminar
     */
    private String shareType;
    private BigInteger mainCourseId;
    private BigInteger subCourseId;
    private BigInteger subCourseTeacherId;
    /**
     * 1,同意，0不同意，null.未处理
     */
    private int status;

    public BigInteger getShareId() { return shareId; }

    public void setShareId(BigInteger shareId) { this.shareId = shareId; }

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

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }



    @Override
    public String toString() {
        return "Share{" +
                "shareId=" + shareId +
                ", shareType=" + shareType +
                ", mainCourseId='" + mainCourseId + '\'' +
                ", subCourseId='" + subCourseId + '\'' +
                ", subCourseTeacherId=" + subCourseTeacherId +
                ", status=" + status +
                '}';
    }
}