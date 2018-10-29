package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "domestic_data_record", schema = "homework_2", catalog = "")
public class DomesticDataRecord {
    private long rid;
    private Date sendTime;
    private Double amount;
    private Double freeAmount;
    private Double paidAmount;
    private Double expense;
    private String userPhone;

    public DomesticDataRecord() {
    }

    public DomesticDataRecord(Date sendTime, Double amount, String userPhone) {
        this.sendTime = sendTime;
        this.amount = amount;
        this.userPhone = userPhone;
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
    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "free_amount")
    public Double getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(Double freeAmount) {
        this.freeAmount = freeAmount;
    }

    @Basic
    @Column(name = "paid_amount")
    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
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
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomesticDataRecord that = (DomesticDataRecord) o;
        return rid == that.rid &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(freeAmount, that.freeAmount) &&
                Objects.equals(paidAmount, that.paidAmount) &&
                Objects.equals(userPhone, that.userPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rid, sendTime, amount, freeAmount, paidAmount, userPhone);
    }
}
