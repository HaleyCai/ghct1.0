package xmu.ghct.crm.entity;

import java.io.Serializable;

public class Klass implements Serializable {
    private int grade;
    private int klassSerial;
    private String klassTime;
    private String klassLocation;

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
                "grade=" + grade +
                ", klassSerial=" + klassSerial +
                ", klassTime='" + klassTime + '\'' +
                ", klassLocation='" + klassLocation + '\'' +
                '}';
    }
}
