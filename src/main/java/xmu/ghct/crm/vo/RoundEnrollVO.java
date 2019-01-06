package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author caiyq
 */
public class RoundEnrollVO {
    private BigInteger klassId;
    private Integer klassSerial;
    private Integer grade;
    private Integer enroll;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    @Override
    public String toString() {
        return "RoundEnrollVO{" +
                "klassId=" + klassId +
                ", klassSerial=" + klassSerial +
                ", grade=" + grade +
                ", enroll=" + enroll +
                '}';
    }
}
