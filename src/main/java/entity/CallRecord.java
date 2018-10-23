package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "call_record", schema = "homework_2", catalog = "")
public class CallRecord {
    private long id;
    private Date beginTime;
    private Long minutes;
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
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        CallRecord that = (CallRecord) o;
        return id == that.id &&
                Objects.equals(beginTime, that.beginTime) &&
                Objects.equals(minutes, that.minutes) &&
                Objects.equals(callerPhone, that.callerPhone) &&
                Objects.equals(calleePhone, that.calleePhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, beginTime, minutes, callerPhone, calleePhone);
    }
}
