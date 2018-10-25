package entity;

import utils.Arith;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "local_data_record", schema = "homework_2", catalog = "")
public class LocalDataRecord {
    private long rid;
    private Date sendTime;
    private Double amount;
    private Double freeAmount;
    private Double paidAmount;
    private Double expense;
    private String userPhone;

    public LocalDataRecord() {
    }

    public LocalDataRecord(Date sendTime, Double amount, String userPhone) {
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
    @Column(name = "sendTime")
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
        LocalDataRecord that = (LocalDataRecord) o;
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

    public void showInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("本地流量使用时间: " + dateFormat.format(sendTime));
        System.out.println("使用者手机: " + userPhone);
        System.out.println("本地流量总量: " + Arith.format(amount) + "M");
        System.out.println("免费本地流量: " + Arith.format(freeAmount) + "M");
        System.out.println("付费本地流量: " + Arith.format(paidAmount) + "M");
        System.out.println("话费支出: " + Arith.format(expense) + "元");
    }
}
