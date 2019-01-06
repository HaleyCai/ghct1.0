package xmu.ghct.crm.vo;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
public class CreatTeamVO {

    BigInteger klassId;
    BigInteger courseId;
    String teamName;
    Integer klassSerial;
    List<BigInteger> studentIdList;

    @Override
    public String toString() {
        return "CreatTeamVO{" +
                "klassId=" + klassId +
                ", courseId=" + courseId +
                ", teamName='" + teamName + '\'' +
                ", klassSerial=" + klassSerial +
                ", studentIdList=" + studentIdList +
                '}';
    }

    public void setKlassId(BigInteger klassId) {
        this.klassId = klassId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setTeamName(String teamName) {
        teamName = teamName;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    public void setStudentIdList(List<BigInteger> studentIdList) {
        this.studentIdList = studentIdList;
    }

    public BigInteger getKlassId() {
        return klassId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getKlassSerial() {
        return klassSerial;
    }

    public List<BigInteger> getStudentIdList() {
        return studentIdList;
    }
}
