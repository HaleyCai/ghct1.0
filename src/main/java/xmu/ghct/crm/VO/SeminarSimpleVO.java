package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 讨论课简单的信息
 */
public class SeminarSimpleVO implements Serializable {
    private BigInteger id;
    private String topic;
    private int order;//讨论课

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SeminarSimpleVO{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", order=" + order +
                '}';
    }
}
