package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basic_price", schema = "homework_2", catalog = "")
public class BasicPrice {
    private long pid;
    private Double callPrice;
    private Double messagePrice;
    private Double localDataPrice;
    private Double domesticDataPrice;

    @Id
    @Column(name = "pid")
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "callPrice")
    public Double getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(Double callPrice) {
        this.callPrice = callPrice;
    }

    @Basic
    @Column(name = "messagePrice")
    public Double getMessagePrice() {
        return messagePrice;
    }

    public void setMessagePrice(Double messagePrice) {
        this.messagePrice = messagePrice;
    }

    @Basic
    @Column(name = "localDataPrice")
    public Double getLocalDataPrice() {
        return localDataPrice;
    }

    public void setLocalDataPrice(Double localDataPrice) {
        this.localDataPrice = localDataPrice;
    }

    @Basic
    @Column(name = "domesticDataPrice")
    public Double getDomesticDataPrice() {
        return domesticDataPrice;
    }

    public void setDomesticDataPrice(Double domesticDataPrice) {
        this.domesticDataPrice = domesticDataPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicPrice that = (BasicPrice) o;
        return pid == that.pid &&
                Objects.equals(callPrice, that.callPrice) &&
                Objects.equals(messagePrice, that.messagePrice) &&
                Objects.equals(localDataPrice, that.localDataPrice) &&
                Objects.equals(domesticDataPrice, that.domesticDataPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pid, callPrice, messagePrice, localDataPrice, domesticDataPrice);
    }
}
