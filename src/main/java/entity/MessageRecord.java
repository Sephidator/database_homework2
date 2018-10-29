package entity;

import utils.Arith;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message_record", schema = "homework_2", catalog = "")
public class MessageRecord {
    private long rid;
    private Date sendTime;
    private Long number;
    private Long freeNum;
    private Long paidNum;
    private Double expense;
    private String senderPhone;
    private String receiverPhone;

    public  MessageRecord() {
    }

    public MessageRecord(Date sendTime, Long number, String senderPhone, String receiverPhone) {
        this.sendTime = sendTime;
        this.number = number;
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
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
    @Column(name = "number")
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Basic
    @Column(name = "free_num")
    public Long getFreeNum() {
        return freeNum;
    }

    public void setFreeNum(Long freeNum) {
        this.freeNum = freeNum;
    }

    @Basic
    @Column(name = "paid_num")
    public Long getPaidNum() {
        return paidNum;
    }

    public void setPaidNum(Long paidNum) {
        this.paidNum = paidNum;
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
        MessageRecord record = (MessageRecord) o;
        return rid == record.rid &&
                Objects.equals(sendTime, record.sendTime) &&
                Objects.equals(number, record.number) &&
                Objects.equals(freeNum, record.freeNum) &&
                Objects.equals(paidNum, record.paidNum) &&
                Objects.equals(senderPhone, record.senderPhone) &&
                Objects.equals(receiverPhone, record.receiverPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rid, sendTime, number, freeNum, paidNum, senderPhone, receiverPhone);
    }

    public void showInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("短信发送时间: " + dateFormat.format(sendTime));
        System.out.println("发送者手机: " + senderPhone);
        System.out.println("接收者手机: " + receiverPhone);
        System.out.println("短信总数量: " + number + "条");
        System.out.println("免费短信数量: " + freeNum + "条");
        System.out.println("付费短信数量: " + paidNum + "条");
        System.out.println("话费支出: " + Arith.format(expense) + "元");
    }
}
