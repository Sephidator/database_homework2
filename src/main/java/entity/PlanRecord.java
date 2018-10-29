package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "plan_record", schema = "homework_2", catalog = "")
public class PlanRecord {
    private long rid;
    private String userPhone;
    private Long pid;
    private Date time;
    private Double expense;
    private Double refund;

    public PlanRecord() {
    }

    public PlanRecord(Date time, Double expense, Double refund, String userPhone, Long pid) {
        this.time = time;
        this.expense = expense;
        this.refund = refund;
        this.userPhone = userPhone;
        this.pid = pid;
    }

    @Id
    @Column(name = "rid")
    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
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
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "expense")
    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    @Basic
    @Column(name = "refund")
    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanRecord that = (PlanRecord) o;
        return rid == that.rid &&
                Objects.equals(time, that.time) &&
                Objects.equals(expense, that.expense) &&
                Objects.equals(refund, that.refund) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(pid, that.pid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rid, time, expense, refund, userPhone, pid);
    }
}
