package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Klass implements Serializable {
    private BigInteger klassId;
    private BigInteger courseId;
    private int grade;
    private int klassSerial;
    private String klassTime;
    private String klassLocation;

    public BigInteger getKlassId() {
        return klassId;
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
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

    public String getKlassTime() {
        return klassTime;
    }

    public void setKlassTime(String klassTime) {
        this.klassTime = klassTime;
    }

    public String getKlassLocation() {
        return klassLocation;
    }

    public void setKlassLocation(String klassLocation) {
        this.klassLocation = klassLocation;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "klassId"+klassId+
                "courseId"+courseId+
                "grade=" + grade +
                ", klassSerial=" + klassSerial +
                ", klassTime='" + klassTime + '\'' +
                ", klassLocation='" + klassLocation + '\'' +
                '}';
    }
}
