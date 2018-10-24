import entity.CallRecord;
import entity.MessageRecord;
import entity.Plan;
import entity.User;
import exception.BalanceException;
import service.PlanService;
import service.RecordService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.UserService;
import utils.HibernateUtil;
import utils.TimeTool;

import java.util.List;

public class Main {
//    public void destroy() {
//        transaction.commit(); //提交事务
//        session.close(); //关闭会话
//        sessionFactory.close(); //关闭会话工厂
//    }

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        PlanService planService = new PlanService(session, transaction);
        RecordService recordService = new RecordService(session, transaction);
        UserService userService = new UserService(session, transaction);

        try {
            System.out.println("#1 订购套餐：");
            User user = session.load(User.class, "13218019068");
            Plan plan = session.load(Plan.class, (long)1);
            planService.orderPlan(user, plan, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }

        try {
            System.out.println("#2 查询套餐：");
            User user = session.load(User.class, "13218019068");
            List<Plan> planList = planService.searchPlan(user);
            for (Plan plan: planList) {
                System.out.println(plan.getPname());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }

//        try {
//            System.out.println("#3 取消套餐：");
//            User user = session.load(User.class, "13218019068");
//            Plan plan = session.load(Plan.class, (long)1);
//            planService.cancelPlan(user, plan, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println();
//        }

        try {
            System.out.println("#4 拨打电话：");
            User caller = session.load(User.class, "13218019068");
            User callee = session.load(User.class, "13780946508");
            if (caller.getBalance() > 0) {
                CallRecord record = new CallRecord(
                        TimeTool.now(), (long)57, caller.getPhone(), callee.getPhone());
                recordService.addCallRecord(record, caller);
            } else {
                throw new BalanceException("错误：无法呼叫用户余额为负数");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }

//        try {
//            System.out.println("#5 充值：");
//            User user = session.load(User.class, "13218019068");
//            System.out.println("充值前余额：" + user.getBalance() + "元");
//            userService.charge(user, (double)900);
//            System.out.println("充值后余额：" + user.getBalance() + "元");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            System.out.println();
//        }

        try {
            System.out.println("#6 发短信：");
            User user = session.load(User.class, "13218019068");
            if (user.getBalance() > 0) {
                MessageRecord record = new MessageRecord(
                        TimeTool.now(), (long)13, user.getPhone(), "13780946508");
                recordService.addMessageRecord(record, user);
            } else {
                throw new BalanceException("错误：无法呼叫，用户余额为负数");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }

        try {
            System.out.println("#7 账单查询：");
            User user = session.load(User.class, "13218019068");
            userService.checkBill(2018, 10, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }

        session.close(); //关闭会话
        sessionFactory.close(); //关闭会话工厂
    }
}
