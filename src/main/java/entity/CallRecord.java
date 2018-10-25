package entity;

import utils.Arith;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "call_record", schema = "homework_2", catalog = "")
public class CallRecord {
    private long rid;
    private Date beginTime;
    private Long minutes;
    private Long freeMinutes;
    private Long paidMinutes;
    private Double expense;
    private String callerPhone;
    private String calleePhone;

    public CallRecord() {
    }

    public CallRecord(Date beginTime, Long minutes, String callerPhone, String calleePhone) {
        this.beginTime = beginTime;
        this.minutes = minutes;
        this.callerPhone = callerPhone;
        this.calleePhone = calleePhone;
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
    @Column(name = "beginTime")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "minutes")
    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    @Basic
    @Column(name = "free_minutes")
    public Long getFreeMinutes() {
        return freeMinutes;
    }

    public void setFreeMinutes(Long freeMinutes) {
        this.freeMinutes = freeMinutes;
    }

    @Basic
    @Column(name = "paid_minutes")
    public Long getPaidMinutes() {
        return paidMinutes;
    }

    public void setPaidMinutes(Long paidMinutes) {
        this.paidMinutes = paidMinutes;
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
    @Column(name = "caller_phone")
    public String getCallerPhone() {
        return callerPhone;
    }

    public void setCallerPhone(String callerPhone) {
        this.callerPhone = callerPhone;
    }

    @Basic
    @Column(name = "callee_phone")
    public String getCalleePhone() {
        return calleePhone;
    }

    public void setCalleePhone(String calleePhone) {
        this.calleePhone = calleePhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallRecord record = (CallRecord) o;
        return rid == record.rid &&
                Objects.equals(beginTime, record.beginTime) &&
                Objects.equals(minutes, record.minutes) &&
                Objects.equals(freeMinutes, record.freeMinutes) &&
                Objects.equals(paidMinutes, record.paidMinutes) &&
                Objects.equals(callerPhone, record.callerPhone) &&
                Objects.equals(calleePhone, record.calleePhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rid, beginTime, minutes, freeMinutes, paidMinutes, callerPhone, calleePhone);
    }

    public void showInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("通话开始时间: " + dateFormat.format(beginTime));
        System.out.println("呼叫者手机: " + callerPhone);
        System.out.println("接听者手机: " + calleePhone);
        System.out.println("通话总时长: " + minutes + "分钟");
        System.out.println("免费通话时长: " + freeMinutes + "分钟");
        System.out.println("付费通话时长: " + paidMinutes + "条");
        System.out.println("话费支出: " + Arith.format(expense) + "元");
    }
}
