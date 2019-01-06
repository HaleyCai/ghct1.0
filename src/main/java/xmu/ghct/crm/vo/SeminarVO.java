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



    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getStatus() {
        return status;
    }

    public int getSeminarSerial() {
        return seminarSerial;
    }

    public int getMaxTeam() {
        return maxTeam;
    }

    public Date getReportDDL() {
        return reportDDL;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public Date getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSeminarSerial(int seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public void setMaxTeam(int maxTeam) {
        this.maxTeam = maxTeam;
    }

    public void setReportDDL(Date reportDDL) {
        this.reportDDL = reportDDL;
    }

    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }


    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public int getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(int roundSerial) {
        this.roundSerial = roundSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public int getKlassSerial() {
        return klassSerial;
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
