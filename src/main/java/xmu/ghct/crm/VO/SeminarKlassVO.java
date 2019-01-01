package xmu.ghct.crm.VO;

import java.math.BigInteger;

public class SeminarKlassVO {
    private BigInteger klassSeminarId;
    private BigInteger courseId;
    private BigInteger klassId;
    private int grade;
    private int klassSerial;

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    @Override
    public String toString() {
        return "SeminarKlassVO{" +
                "klassSeminarId=" + klassSeminarId +
                ", courseId=" + courseId +
                ", klassId=" + klassId +
                ", grade=" + grade +
                ", klassSerial=" + klassSerial +
                '}';
    }
}
