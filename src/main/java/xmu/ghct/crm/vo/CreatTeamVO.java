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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public List<BigInteger> getStudentIdList() {
        return studentIdList;
    }

    public void setStudentIdList(List<BigInteger> studentIdList) {
        this.studentIdList = studentIdList;
    }
}
