package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Seminar implements Serializable {
    private BigInteger id;
    private BigInteger courseId;
    private BigInteger roundId;
    private String seminarName;
    private String introduction;
    private String maxTeam;//本次讨论课最多报名队伍数
    private int visible;//0不可见，1可见
    private int seminarSerial;//讨论课序号
    private Date enrollStartTime;//讨论课报名开始时间
    private Date enrollEndTime;//讨论课报名结束时间

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(String maxTeam) {
        this.maxTeam = maxTeam;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(int seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Date getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    @Override
    public String toString() {
        return "Seminar{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", roundId=" + roundId +
                ", seminarName='" + seminarName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", maxTeam='" + maxTeam + '\'' +
                ", visible=" + visible +
                ", seminarSerial=" + seminarSerial +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                '}';
    }
}
