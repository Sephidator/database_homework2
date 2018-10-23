package service;

import entity.Orders;
import entity.Plan;
import entity.User;
import exception.BalanceException;
import exception.DatabaseException;
import exception.DuplicateException;
import exception.NotExistsException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.Arith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanService extends Service{

    public PlanService(Session session, Transaction transaction) {
        super(session, transaction);
    }

    public List<Plan> searchPlan(User user){
        openTransaction();
        String hql = "SELECT p FROM Orders o, Plan p WHERE o.userPhone = :user_phone AND o.pid = p.pid";
        Query query = session.createQuery(hql).setParameter("user_phone", user.getPhone());
        return query.list();
    }

    public void orderPlan(User user, Plan plan, boolean subscribe) throws Exception {
        openTransaction();
        List<Orders> applicableOrders = getApplicableOrders(user, plan);

        if (applicableOrders.size() == 1) {
            throw new DuplicateException("错误：用户已经订阅该套餐");
        } else if (applicableOrders.size() > 1) {
            throw new DatabaseException("错误：数据库错误，用户重复订阅套餐");
        } else if (user.getBalance() < plan.getPrice()) {
            throw new BalanceException("错误：用户余额不足");
        } else {
            Orders order = new Orders(user.getPhone(), plan.getPid(),
                    new Date(), null, subscribe, true);

            user.deductBalance(plan.getPrice());
            user.addFreeCallMinutes(plan.getFreeCall());
            user.addFreeMessageNum(plan.getFreeMessage());
            user.addFreeLocalData(plan.getFreeLocalData());
            user.addFreeDomesticData(plan.getFreeDomesticData());

            session.save(user);
            session.save(order);
            transaction.commit();
        }
    }

    public void cancelPlan(User user, Plan plan, boolean cancelNow) throws Exception {
        openTransaction();
        List<Orders> applicableOrders = getApplicableOrders(user, plan);

        if (applicableOrders.size() == 0) {
            throw new NotExistsException("错误：用户未订阅该套餐");
        } else if (applicableOrders.size() > 1) {
            throw new DatabaseException("错误：数据库错误，用户重复订阅套餐");
        } else {
            Orders order = applicableOrders.get(0);
            if (!order.getSubscribe()) {
                throw new DuplicateException("错误：用户已经取消订阅该套餐");
            } else {
                order.setSubscribe(false);
                if (cancelNow) {
                    order.setEndTime(new Date());
                    order.setApplicable(false);
                    handleRefund(user, plan);
                } else {
                    order.setEndTime(lastDayThisMonth());
                }
                session.save(user);
                session.save(order);
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

    private void handleRefund(User user, Plan plan) {
        long unusedCall = user.deductFreeCallMinutes(plan.getFreeCall());
        long unusedMessage = user.deductFreeMessageNum(plan.getFreeMessage());
        double unusedLocalData = user.deductFreeLocalData(plan.getFreeLocalData());
        double unusedDomesticData = user.deductFreeDomesticData(plan.getFreeDomesticData());

        double r1 = plan.getFreeCall()!=0 ? Arith.div((double)unusedCall, (double)plan.getFreeCall()) : 1;
        double r2 = plan.getFreeMessage()!=0 ? Arith.div((double)unusedMessage, (double)plan.getFreeMessage()) : 1;
        double r3 = plan.getFreeLocalData()!=0 ? Arith.div(unusedLocalData, plan.getFreeLocalData()) : 1;
        double r4 = plan.getFreeDomesticData()!=0 ? Arith.div(unusedDomesticData, plan.getFreeDomesticData()) : 1;

        double rate = Arith.div(Arith.add(r1, r2, r3, r4), 4);
        double refund = Arith.mul(plan.getPrice(), rate);
        user.addBalance(refund);
    }

    private Date lastDayThisMonth() {
        Calendar day = Calendar.getInstance();
        day.setTime(new Date());
        day.set(Calendar.DAY_OF_MONTH, day.getActualMaximum(Calendar.DAY_OF_MONTH));
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        return day.getTime();
    }
}
