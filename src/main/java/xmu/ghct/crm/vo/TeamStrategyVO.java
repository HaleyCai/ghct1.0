package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class TeamStrategyVO {
    BigInteger courseId;
    int strategySerial;
    BigInteger strategyId;
    String strategyName;


    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setStrategySerial(int strategySerial) {
        this.strategySerial = strategySerial;
    }

    public void setStrategyId(BigInteger strategyId) {
        this.strategyId = strategyId;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public int getStrategySerial() {
        return strategySerial;
    }

    public BigInteger getStrategyId() {
        return strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    @Override
    public String toString() {
        return "TeamStrategyVO{" +
                "courseId=" + courseId +
                ", strategySerial=" + strategySerial +
                ", strategyId=" + strategyId +
                ", strategyName='" + strategyName + '\'' +
                '}';
    }
}
