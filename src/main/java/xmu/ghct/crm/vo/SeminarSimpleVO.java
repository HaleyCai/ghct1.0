package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author caiyq
 */
public class SeminarSimpleVO implements Serializable {
    private BigInteger seminarId;
    private BigInteger klassSeminarId;
    private String topic;
    //讨论课
    private int order;


    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

    public BigInteger getId() {
        return seminarId;
    }

    public void setId(BigInteger seminarId) {
        this.seminarId = seminarId;
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
                "id=" + seminarId +
                ", topic='" + topic + '\'' +
                ", order=" + order +
                '}';
    }
}
