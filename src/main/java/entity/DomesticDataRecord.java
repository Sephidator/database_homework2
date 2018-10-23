package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "domestic_data_record", schema = "homework_2", catalog = "")
public class DomesticDataRecord {
    private long id;
    private Date sendTime;
    private Double amount;
    private String userPhone;

    public DomesticDataRecord() {
    }

    public DomesticDataRecord(Date sendTime, Double amount, String userPhone) {
        this.sendTime = sendTime;
        this.amount = amount;
        this.userPhone = userPhone;
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
        return id == that.id &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(userPhone, that.userPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sendTime, amount, userPhone);
    }
}
