package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class TeamStrategyVO {
    private BigInteger courseId;
    private Integer strategySerial;
    private BigInteger strategyId;
    private String strategyName;

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public Integer getStrategySerial() {
        return strategySerial;
    }

    public void setStrategySerial(Integer strategySerial) {
        this.strategySerial = strategySerial;
    }

    public BigInteger getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(BigInteger strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
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
