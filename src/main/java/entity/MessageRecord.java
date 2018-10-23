package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message_record", schema = "homework_2", catalog = "")
public class MessageRecord {
    private long id;
    private Date sendTime;
    private Long number;
    private String senderPhone;
    private String receiverPhone;

    public MessageRecord() {
    }

    public MessageRecord(Date sendTime, Long number, String senderPhone, String receiverPhone) {
        this.sendTime = sendTime;
        this.number = number;
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
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
    @Column(name = "number")
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Basic
    @Column(name = "sender_phone")
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    @Basic
    @Column(name = "receiver_phone")
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRecord that = (MessageRecord) o;
        return id == that.id &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(number, that.number) &&
                Objects.equals(senderPhone, that.senderPhone) &&
                Objects.equals(receiverPhone, that.receiverPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sendTime, number, senderPhone, receiverPhone);
    }
}
