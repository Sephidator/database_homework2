package service;

import entity.Orders;
import entity.Plan;
import entity.PlanRecord;
import entity.User;
import exception.BalanceException;
import exception.DatabaseException;
import exception.DuplicateException;
import exception.NotExistsException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.Arith;
import utils.TimeTool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PlanService extends Service{

    public PlanService(Session session, Transaction transaction) {
        super(session, transaction);
    }

    public List<Plan> searchPlan(User user){
        openTransaction();
        String hql = "SELECT p FROM Orders o, Plan p WHERE o.userPhone = :user_phone AND o.pid = p.pid";
        Query query = session.createQuery(hql).setParameter("user_phone", user.getPhone());
        List<Plan> plans = query.list();
        for(Plan plan: plans) {
            Set<Orders> orderList = plan.getOrderList();
            for(Orders order: orderList) {
                if (!order.getUserPhone().equals(user.getPhone())) {
                    orderList.remove(order);
                }
            }
        }
        return plans;
    }

    public void orderPlan(User user, Plan plan, boolean renewal) throws DuplicateException, DatabaseException, BalanceException {
        openTransaction();
        List<Orders> applicableOrders = getApplicableOrders(user, plan);

        if (applicableOrders.size() == 1) {
            throw new DuplicateException("错误: 用户已经订阅该套餐");
        } else if (applicableOrders.size() > 1) {
            throw new DatabaseException("错误: 数据库错误，用户重复订阅套餐");
        } else if (user.getBalance() < plan.getPrice()) {
            throw new BalanceException("错误: 用户余额不足");
        } else {
            Date now = TimeTool.now();
            Orders order = new Orders(user.getPhone(), plan.getPid(),
                    now, null, renewal, true);
            PlanRecord planRecord = new PlanRecord(now, plan.getPrice(),
                    0.0, user.getPhone(), plan.getPid());

            user.deductBalance(plan.getPrice());
            user.addFreeCallMinutes(plan.getFreeCall());
            user.addFreeMessageNum(plan.getFreeMessage());
            user.addFreeLocalData(plan.getFreeLocalData());
            user.addFreeDomesticData(plan.getFreeDomesticData());

            session.save(user);
            session.save(order);
            session.save(planRecord);
            transaction.commit();
        }
    }

    public void cancelPlan(User user, Plan plan, boolean cancelNow) throws NotExistsException, DatabaseException, DuplicateException {
        openTransaction();
        List<Orders> applicableOrders = getApplicableOrders(user, plan);

        if (applicableOrders.size() == 0) {
            throw new NotExistsException("错误: 用户未订阅该套餐");
        } else if (applicableOrders.size() > 1) {
            throw new DatabaseException("错误: 数据库错误，用户重复订阅套餐");
        } else {
            Orders order = applicableOrders.get(0);

            if (!order.getRenewal()) {
                throw new DuplicateException("错误: 用户已经不续约该套餐");
            } else {
                double refund = 0;
                Date now = TimeTool.now();

                if (cancelNow) {
                    order.setEndTime(TimeTool.now());
                    order.setApplicable(false);
                    refund = handleRefund(user, plan);
                } else {
                    order.setEndTime(TimeTool.lastDayOfMonth());
                }
                order.setRenewal(false);
                PlanRecord planRecord = new PlanRecord(now, 0.0,
                        refund, user.getPhone(), plan.getPid());

                session.save(user);
                session.save(order);
                session.save(planRecord);
                transaction.commit();
            }
        }
    }

    private List<Orders> getApplicableOrders(User user, Plan plan) {
        String hql = "FROM Orders o WHERE o.userPhone = :user_phone AND o.pid = :pid" +
                " AND o.applicable = true";
        Query query = session.createQuery(hql)
                .setParameter("user_phone", user.getPhone())
                .setParameter("pid", plan.getPid())
                .setMaxResults(1);
        return query.list();
    }

    private Double handleRefund(User user, Plan plan) {
        long unusedCall = user.deductFreeCallMinutes(plan.getFreeCall());
        long unusedMessage = user.deductFreeMessageNum(plan.getFreeMessage());
        double unusedLocalData = user.deductFreeLocalData(plan.getFreeLocalData());
        double unusedDomesticData = user.deductFreeDomesticData(plan.getFreeDomesticData());

        // r1~r4对应通话、短信、本地流量、国内流量中"未使用的优惠占该种优惠的比率"
        // n计算了r1～r4中非零数的个数，为了计算rate，即"所有未使用的优惠占所有优惠的比率"
        double r1 = plan.getFreeCall()!=0 ? Arith.div(unusedCall, plan.getFreeCall()) : 0;
        double r2 = plan.getFreeMessage()!=0 ? Arith.div(unusedMessage, plan.getFreeMessage()) : 0;
        double r3 = plan.getFreeLocalData()!=0 ? Arith.div(unusedLocalData, plan.getFreeLocalData()) : 0;
        double r4 = plan.getFreeDomesticData()!=0 ? Arith.div(unusedDomesticData, plan.getFreeDomesticData()) : 0;
        int n = (r1!=0?1:0) + (r2!=0?1:0) +(r3!=0?1:0) + (r4!=0?1:0);

        double rate = Arith.div(Arith.add(r1, r2, r3, r4), n);
        double refund = Arith.mul(plan.getPrice(), rate);
        user.addBalance(refund);

        return refund;
    }
}
