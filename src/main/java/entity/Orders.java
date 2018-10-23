package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Orders {
    private long orderno;
    private String userPhone;
    private Long pid;
    private Date beginTime;
    private Date endTime;
    private Boolean subscribe;
    private Boolean applicable;

    public Orders() {
    }

    public Orders(String userPhone, Long pid, Date beginTime, Date endTime, Boolean subscribe, Boolean applicable) {
        this.userPhone = userPhone;
        this.pid = pid;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.subscribe = subscribe;
        this.applicable = applicable;
    }

    @Id
    @Column(name = "orderno")
    public long getOrderno() {
        return orderno;
    }

    public void setOrderno(long orderno) {
        this.orderno = orderno;
    }

    @Basic
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Basic
    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "beginTime")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "endTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "subscribe")
    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    @Basic
    @Column(name = "applicable")
    public Boolean getApplicable() {
        return applicable;
    }

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return orderno == orders.orderno &&
                Objects.equals(userPhone, orders.userPhone) &&
                Objects.equals(pid, orders.pid) &&
                Objects.equals(beginTime, orders.beginTime) &&
                Objects.equals(endTime, orders.endTime) &&
                Objects.equals(subscribe, orders.subscribe) &&
                Objects.equals(applicable, orders.applicable);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderno, userPhone, pid, beginTime, endTime, subscribe, applicable);
    }
}
