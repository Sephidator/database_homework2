package entity;

import utils.Arith;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    private String phone;
    private String uname;
    private Double balance;
    private String province;
    private Long freeCallMinutes;
    private Long freeMessageNum;
    private Double freeLocalData;
    private Double freeDomesticData;

    @Id
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "uname")
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Basic
    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addBalance(Double amount){
        this.balance = Arith.add(this.balance, amount);
    }

    public void deductBalance(Double price){
        this.balance = Arith.sub(this.balance, price);
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "freeCallMinutes")
    public Long getFreeCallMinutes() {
        return freeCallMinutes;
    }

    public void setFreeCallMinutes(Long freeCallMinutes) {
        this.freeCallMinutes = freeCallMinutes;
    }

    public void addFreeCallMinutes(Long freeCallMinutes) {
        this.freeCallMinutes += freeCallMinutes;
    }

    /**
     * 扣除免费通话时长，不够扣除的场合免费通话时长清0
     * @param minutes 要扣除的通话时长（分钟）
     * @return 实际扣除的免费通话时长
     */
    public Long deductFreeCallMinutes(Long minutes) {
        long result = this.freeCallMinutes > minutes ? minutes : this.freeCallMinutes;
        this.freeCallMinutes = this.freeCallMinutes - result;
        return result;
    }

    @Basic
    @Column(name = "freeMessageNum")
    public Long getFreeMessageNum() {
        return freeMessageNum;
    }

    public void setFreeMessageNum(Long freeMessageNum) {
        this.freeMessageNum = freeMessageNum;
    }

    public void addFreeMessageNum(Long freeMessageNum) {
        this.freeMessageNum += freeMessageNum;
    }

    /**
     * 扣除免费短信数量，不够扣除的场合免费短信数量清0
     * @param messageNum 要扣除的短信数量（条）
     * @return 实际扣除的免费短信数量
     */
    public Long deductFreeMessageNum(Long messageNum) {
        long result = this.freeMessageNum > messageNum ? messageNum : this.freeMessageNum;
        this.freeMessageNum = this.freeMessageNum - result;
        return result;
    }

    @Basic
    @Column(name = "freeLocalData")
    public Double getFreeLocalData() {
        return freeLocalData;
    }

    public void setFreeLocalData(Double freeLocalData) {
        this.freeLocalData = freeLocalData;
    }

    public void addFreeLocalData(Double freeLocalData) {
        this.freeLocalData = Arith.add(this.freeLocalData, freeLocalData);
    }

    /**
     * 扣除免费本地流量，不够扣除的场合免费本地流量清0
     * @param localData 要扣除的本地流量（M）
     * @return 实际扣除的免费本地流量
     */
    public Double deductFreeLocalData(Double localData) {
        double result = this.freeLocalData > localData ? localData : this.freeLocalData;
        this.freeLocalData = this.freeLocalData - result;
        return result;
    }

    @Basic
    @Column(name = "freeDomesticData")
    public Double getFreeDomesticData() {
        return freeDomesticData;
    }

    public void setFreeDomesticData(Double freeDomesticData) {
        this.freeDomesticData = freeDomesticData;
    }

    public void addFreeDomesticData(Double freeDomesticData) {
        this.freeDomesticData = Arith.add(this.freeDomesticData, freeDomesticData);
    }

    /**
     * 扣除免费国内流量，不够扣除的场合免费国内流量清0
     * @param domesticData 要扣除的国内流量（M）
     * @return 实际扣除的免费国内流量
     */
    public Double deductFreeDomesticData(Double domesticData) {
        double result = this.freeDomesticData > domesticData ? domesticData : this.freeDomesticData;
        this.freeDomesticData = this.freeDomesticData - result;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(phone, user.phone) &&
                Objects.equals(uname, user.uname) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(province, user.province) &&
                Objects.equals(freeMessageNum, user.freeMessageNum) &&
                Objects.equals(freeCallMinutes, user.freeCallMinutes) &&
                Objects.equals(freeLocalData, user.freeLocalData) &&
                Objects.equals(freeDomesticData, user.freeDomesticData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(phone, uname, balance, province, freeMessageNum, freeCallMinutes, freeLocalData, freeDomesticData);
    }
}
