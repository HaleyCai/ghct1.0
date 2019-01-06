package xmu.ghct.crm.vo;

import java.math.BigInteger;

/**
 * @author hzm
 */
public class AndOrOrStrategyVO {

    BigInteger id;
    String strategyName;
    BigInteger strategyId;

    public BigInteger getId() {
        return id;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public BigInteger getStrategyId() {
        return strategyId;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public void setStrategyId(BigInteger strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public String toString() {
        return "AndOrOrStrategyVO{" +
                "id=" + id +
                ", strategyName='" + strategyName + '\'' +
                ", strategyId=" + strategyId +
                '}';
    }
}
