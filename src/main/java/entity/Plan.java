package entity;

import utils.Arith;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Plan {
    private long pid;
    private String pname;
    private Double price;
    private Long freeCallMinutes;
    private Long freeMessageNum;
    private Double freeLocalData;
    private Double freeDomesticData;
    private Set<Orders> orderList = new HashSet<>();

    @Id
    @Column(name = "pid")
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "pname")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "free_call_minutes")
    public Long getFreeCallMinutes() {
        return freeCallMinutes;
    }

    public void setFreeCallMinutes(Long freeCallMinutes) {
        this.freeCallMinutes = freeCallMinutes;
    }

    @Basic
    @Column(name = "free_message_num")
    public Long getFreeMessageNum() {
        return freeMessageNum;
    }

    public void setFreeMessageNum(Long freeMessageNum) {
        this.freeMessageNum = freeMessageNum;
    }

    @Basic
    @Column(name = "free_local_data")
    public Double getFreeLocalData() {
        return freeLocalData;
    }

    public void setFreeLocalData(Double freeLocalData) {
        this.freeLocalData = freeLocalData;
    }

    @Basic
    @Column(name = "free_domestic_data")
    public Double getFreeDomesticData() {
        return freeDomesticData;
    }

    public void setFreeDomesticData(Double freeDomesticData) {
        this.freeDomesticData = freeDomesticData;
    }

    public Set<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(Set<Orders> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return pid == plan.pid &&
                Objects.equals(pname, plan.pname) &&
                Objects.equals(price, plan.price) &&
                Objects.equals(freeCallMinutes, plan.freeCallMinutes) &&
                Objects.equals(freeMessageNum, plan.freeMessageNum) &&
                Objects.equals(freeLocalData, plan.freeLocalData) &&
                Objects.equals(freeDomesticData, plan.freeDomesticData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pid, pname, price, freeCallMinutes, freeMessageNum, freeLocalData, freeDomesticData);
    }

    public void showInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("套餐名: " + pname);
        System.out.println("套餐价格: " + Arith.format(price) + "元");

        StringBuilder info = new StringBuilder();
        String toPrint = "";

        toPrint = freeCallMinutes!=0 ? "免费通话时长" + freeCallMinutes + "分钟" : "";
        info.append(toPrint);

        toPrint = freeMessageNum!=0 ? "免费短信数量" + freeMessageNum + "条" : "";
        info.append((!info.toString().equals("")&&!toPrint.equals("")) ? ", " : "");
        info.append(toPrint);

        toPrint = freeLocalData!=0 ? "免费本地流量" + Arith.format(freeLocalData) + "M" : "";
        info.append((!info.toString().equals("")&&!toPrint.equals("")) ? ", " : "");
        info.append(toPrint);

        toPrint = freeDomesticData!=0 ? "免费国内流量" + Arith.format(freeDomesticData) + "M" : "";
        info.append((!info.toString().equals("")&&!toPrint.equals("")) ? ", " : "");
        info.append(toPrint);

        System.out.println("套餐优惠: " + info.toString());

        System.out.println("订阅记录: ");
        for(Orders order: orderList) {
            System.out.print("    订阅日期: " + dateFormat.format(order.getBeginTime()) + ", ");
            if (order.getEndTime() != null) {
                System.out.print("退订日期: " + dateFormat.format(order.getEndTime()) + ", ");
            }
            System.out.print("订阅状态: " + (order.getApplicable() ? "订阅中, " : "已退订, "));
            System.out.println("续约状态: " + (order.getRenewal() ? "续约中" : "不续约"));
        }
    }
}
