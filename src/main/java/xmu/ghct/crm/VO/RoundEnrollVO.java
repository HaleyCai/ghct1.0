package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class RoundEnrollVO {
    private BigInteger klassId;
    private int klassSerial;
    private int grade;

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
}
