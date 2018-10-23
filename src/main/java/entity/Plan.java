package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Plan {
    private long pid;
    private String pname;
    private Double price;
    private Long freeCall;
    private Long freeMessage;
    private Double freeLocalData;
    private Double freeDomesticData;

    @Id
    @Column(name = "pid")
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "pname")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "freeCall")
    public Long getFreeCall() {
        return freeCall;
    }

    public void setFreeCall(Long freeCall) {
        this.freeCall = freeCall;
    }

    @Basic
    @Column(name = "freeMessage")
    public Long getFreeMessage() {
        return freeMessage;
    }

    public void setFreeMessage(Long freeMessage) {
        this.freeMessage = freeMessage;
    }

    @Basic
    @Column(name = "freeLocalData")
    public Double getFreeLocalData() {
        return freeLocalData;
    }

    public void setFreeLocalData(Double freeLocalData) {
        this.freeLocalData = freeLocalData;
    }

    @Basic
    @Column(name = "freeDomesticData")
    public Double getFreeDomesticData() {
        return freeDomesticData;
    }

    public void setFreeDomesticData(Double freeDomesticData) {
        this.freeDomesticData = freeDomesticData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return pid == plan.pid &&
                Objects.equals(pname, plan.pname) &&
                Objects.equals(price, plan.price) &&
                Objects.equals(freeCall, plan.freeCall) &&
                Objects.equals(freeMessage, plan.freeMessage) &&
                Objects.equals(freeLocalData, plan.freeLocalData) &&
                Objects.equals(freeDomesticData, plan.freeDomesticData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pid, pname, price, freeCall, freeMessage, freeLocalData, freeDomesticData);
    }
}
