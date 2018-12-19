package xmu.ghct.crm.entity;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Seminar implements Serializable {

    private BigInteger seminarId;
    private  BigInteger courseId;
    private  BigInteger roundId;
    private String seminarName;
    private String introduction;
    private int maxTeam;
    private Boolean visible;
    private int seminarSerial;
    private Date enrollStartTime;
    private Date enrollEndTime;


    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMaxTeam(int maxTeam) {
        this.maxTeam = maxTeam;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setSeminarSerial(int seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getMaxTeam() {
        return maxTeam;
    }

    public Boolean isVisible() {
        return visible;
    }

    public int getSeminarSerial() {
        return seminarSerial;
    }


    @Override
    public String toString() {
        return "Seminar{" +
                "seminarId=" + seminarId +
                ", courseId=" + courseId +
                ", roundId=" + roundId +
                ", seminarName='" + seminarName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", maxTeam=" + maxTeam +
                ", visible=" + visible +
                ", seminarSerial=" + seminarSerial +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                '}';
    }

}
