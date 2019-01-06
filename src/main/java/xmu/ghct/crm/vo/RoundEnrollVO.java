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

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getEnroll() {
        return enroll;
    }

    public void setEnroll(int enroll) {
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
