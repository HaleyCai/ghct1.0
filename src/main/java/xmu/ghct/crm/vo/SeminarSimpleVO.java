package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author caiyq
 */
public class SeminarSimpleVO implements Serializable {
    private BigInteger id;
    private BigInteger klassSeminarId;
    private String topic;
    private Integer order;


    public void setKlassSeminarId(BigInteger klassSeminarId) {
        this.klassSeminarId = klassSeminarId;
    }

    public BigInteger getKlassSeminarId() {
        return klassSeminarId;
    }

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

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SeminarSimpleVO{" +
                "id=" + id +
                ", klassSeminarId=" + klassSeminarId +
                ", topic='" + topic + '\'' +
                ", order=" + order +
                '}';
    }
}
