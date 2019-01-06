package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class ShareTeamVO implements Serializable {
    private BigInteger mainCourseId;
    private BigInteger subCourseId;
    private BigInteger subCourseTeacherId;
    private int status;//1同意，0不同意

    public BigInteger getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(BigInteger mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public BigInteger getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(BigInteger subCourseId) {
        this.subCourseId = subCourseId;
    }

    public BigInteger getSubCourseTeacherId() {
        return subCourseTeacherId;
    }

    public void setSubCourseTeacherId(BigInteger subCourseTeacherId) {
        this.subCourseTeacherId = subCourseTeacherId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShareTeamVO{" +
                "mainCourseId=" + mainCourseId +
                ", subCourseId=" + subCourseId +
                ", subCourseTeacherId=" + subCourseTeacherId +
                ", status=" + status +
                '}';
    }
}
