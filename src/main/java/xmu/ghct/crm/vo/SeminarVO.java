package xmu.ghct.crm.vo;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author hzm
 */
public class SeminarVO {

    private BigInteger klassId;
    private Integer klassSerial;
    private BigInteger klassSeminarId;
    private BigInteger seminarId;
    private String seminarName;
    private String introduction;
    private Integer status;
    private Integer seminarSerial;
    private Integer maxTeam;
    private Date reportDDL;
    private BigInteger roundId;
    private Integer roundSerial;
    private Date enrollStartTime;
    private Date enrollEndTime;

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(Integer seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Date getReportDDL() {
        return reportDDL;
    }

    public void setReportDDL(Date reportDDL) {
        this.reportDDL = reportDDL;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public Integer getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Integer roundSerial) {
        this.roundSerial = roundSerial;
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
        return "SeminarVO{" +
                "klassId=" + klassId +
                ", klassSerial=" + klassSerial +
                ", klassSeminarId=" + klassSeminarId +
                ", seminarId=" + seminarId +
                ", seminarName='" + seminarName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", status=" + status +
                ", seminarSerial=" + seminarSerial +
                ", maxTeam=" + maxTeam +
                ", reportDDL=" + reportDDL +
                ", roundId=" + roundId +
                ", roundSerial=" + roundSerial +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                '}';
    }

}
