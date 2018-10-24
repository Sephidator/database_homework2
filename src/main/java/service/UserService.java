package service;

import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.Arith;

import java.util.List;


public class UserService extends Service{
    public UserService(Session session, Transaction transaction) {
        super(session, transaction);
    }

    public void charge(User user, Double amount) {
        openTransaction();
        user.addBalance(amount);

        session.save(user);
        transaction.commit();
    }

    public void checkBill(int year, int month, User user) {
        System.out.println("--------------------");
        System.out.println("用户账单（" + year + "年" + month + "月）：");
        System.out.println("用户名：" + user.getUname() + "，手机号：" + user.getPhone());
        System.out.println();

        double callExpense = checkCall(year, month, user);
        double messageExpense = checkMessage(year, month, user);
        double localDataExpense = checkLocalData(year, month, user);
        double domesticDataExpense = checkDomesticData(year, month, user);
        double planExpense = checkPlan(year, month, user);

        double totalExpense = Arith.add(callExpense, messageExpense,
                localDataExpense, domesticDataExpense, planExpense);
        System.out.println("当月话费总额：" + totalExpense + "元");
        System.out.println("--------------------");
    }

    /**
     * 查询某年某月的通话情况，输出免费通话时长，付费通话时长和产生的通话费用
     * @param year 年
     * @param month 月
     * @param user 用户对象
     * @return 产生的通话费用
     */
    public Double checkCall(int year, int month, User user) {
        long freeCallMinutes = 0;
        long paidCallMinutes = 0;
        double callExpense = 0;

        String hql = "FROM CallRecord cr WHERE cr.callerPhone = :userPhone AND " +
                "year(cr.beginTime) = :year AND month(cr.beginTime) = :month";
        Query query = session.createQuery(hql)
                .setParameter("userPhone", user.getPhone())
                .setParameter("year", year)
                .setParameter("month", month);
        List<CallRecord> callRecords =  query.list();

        for (CallRecord record: callRecords) {
            freeCallMinutes += record.getFreeMinutes();
            paidCallMinutes += record.getPaidMinutes();
            callExpense = Arith.add(callExpense, record.getExpense());
        }

        System.out.println("通话情况：");
        System.out.println("    免费通话时长：" + freeCallMinutes + "分钟");
        System.out.println("    付费通话时长：" + paidCallMinutes + "分钟");
        System.out.println("    用户通话费用：" + callExpense + "元" + System.lineSeparator());
        return callExpense;
    }

    /**
     * 查询某年某月的短信情况，输出免费短信数，付费短信数和产生的短信费用
     * @param year 年
     * @param month 月
     * @param user 用户对象
     * @return 产生的短信费用
     */
    public Double checkMessage(int year, int month, User user) {
        long freeMessageNum = 0;
        long paidMessageNum = 0;
        double messageExpense = 0;

        String hql = "FROM MessageRecord mr WHERE mr.senderPhone = :userPhone AND " +
                "year(mr.sendTime) = :year AND month(mr.sendTime) = :month";
        Query query = session.createQuery(hql)
                .setParameter("userPhone", user.getPhone())
                .setParameter("year", year)
                .setParameter("month", month);
        List<MessageRecord> messageRecords =  query.list();

        for (MessageRecord record: messageRecords) {
            freeMessageNum += record.getFreeNum();
            paidMessageNum += record.getPaidNum();
            messageExpense = Arith.add(messageExpense, record.getExpense());
        }

        System.out.println("短信使用情况：");
        System.out.println("    免费短信数量：" + freeMessageNum + "条");
        System.out.println("    付费短信数量：" + paidMessageNum + "条");
        System.out.println("    用户短信费用：" + messageExpense + "元" + System.lineSeparator());
        return messageExpense;
    }

    /**
     * 查询某年某月的本地流量使用情况，输出免费本地流量，付费本地流量和产生的本地流量费用
     * @param year 年
     * @param month 月
     * @param user 用户对象
     * @return 产生的本地流量费用
     */
    public Double checkLocalData(int year, int month, User user) {
        double freeAmount = 0;
        double paidAmount = 0;
        double localDataExpense = 0;

        String hql = "FROM LocalDataRecord ldr WHERE ldr.userPhone = :userPhone AND " +
                "year(ldr.sendTime) = :year AND month(ldr.sendTime) = :month";
        Query query = session.createQuery(hql)
                .setParameter("userPhone", user.getPhone())
                .setParameter("year", year)
                .setParameter("month", month);
        List<LocalDataRecord> localDataRecords =  query.list();

        for (LocalDataRecord record: localDataRecords) {
            freeAmount = Arith.add(freeAmount, record.getFreeAmount());
            paidAmount = Arith.add(paidAmount, record.getPaidAmount());
            localDataExpense = Arith.add(localDataExpense, record.getExpense());
        }

        System.out.println("本地流量使用情况：");
        System.out.println("    免费本地流量：" + freeAmount + "M");
        System.out.println("    付费本地流量：" + paidAmount + "M");
        System.out.println("    本地流量费用：" + localDataExpense + "元" + System.lineSeparator());
        return localDataExpense;
    }

    /**
     * 查询某年某月的国内流量使用情况，输出免费国内流量，付费国内流量和产生的国内流量费用
     * @param year 年
     * @param month 月
     * @param user 用户对象
     * @return 产生的国内流量费用
     */
    public Double checkDomesticData(int year, int month, User user) {
        double freeAmount = 0;
        double paidAmount = 0;
        double domesticDataExpense = 0;

        String hql = "FROM DomesticDataRecord ddr WHERE ddr.userPhone = :userPhone AND " +
                "year(ddr.sendTime) = :year AND month(ddr.sendTime) = :month";
        Query query = session.createQuery(hql)
                .setParameter("userPhone", user.getPhone())
                .setParameter("year", year)
                .setParameter("month", month);
        List<DomesticDataRecord> domesticDataRecords =  query.list();

        for (DomesticDataRecord record: domesticDataRecords) {
            freeAmount = Arith.add(freeAmount, record.getFreeAmount());
            paidAmount = Arith.add(paidAmount, record.getPaidAmount());
            domesticDataExpense = Arith.add(domesticDataExpense, record.getExpense());
        }

        System.out.println("国内流量使用情况：");
        System.out.println("    免费国内流量：" + freeAmount + "M");
        System.out.println("    付费国内流量：" + paidAmount + "M");
        System.out.println("    国内流量费用：" + domesticDataExpense + "元" + System.lineSeparator());
        return domesticDataExpense;
    }

    /**
     * 查询某年某月的套餐费用，输出套餐订阅费用，退订套餐退款和实际套餐费用
     * @param year 年
     * @param month 月
     * @param user 用户对象
     * @return 产生实际套餐费用
     */
    public Double checkPlan(int year, int month, User user) {
        double orderExpense = 0;
        double refund = 0;
        double planExpense = 0;

        String hql = "FROM PlanRecord pr WHERE pr.userPhone = :userPhone AND " +
                "year(pr.time) = :year AND month(pr.time) = :month";
        Query query = session.createQuery(hql)
                .setParameter("userPhone", user.getPhone())
                .setParameter("year", year)
                .setParameter("month", month);
        List<PlanRecord> planRecords =  query.list();

        for (PlanRecord record: planRecords) {
            orderExpense = Arith.add(orderExpense, record.getExpense());
            refund = Arith.add(refund, record.getRefund());
            planExpense = Arith.sub(orderExpense, refund);
        }

        System.out.println("套餐费用：");
        System.out.println("    套餐订阅费用：" + orderExpense + "元");
        System.out.println("    退订套餐退款：" + refund + "元");
        System.out.println("    实际套餐费用：" + planExpense + "元" + System.lineSeparator());
        return planExpense;
    }
}
