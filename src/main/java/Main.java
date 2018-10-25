import entity.CallRecord;
import entity.LocalDataRecord;
import entity.Plan;
import entity.User;
import exception.BalanceException;
import service.PlanService;
import service.RecordService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.UserService;
import utils.FileTool;
import utils.HibernateUtil;
import utils.TimeTool;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private static PlanService planService;
    private static RecordService recordService;
    private static UserService userService;
    private static long startTime;
    private static long endTime;

    public static void main(String[] args) {

        System.out.println("# 初始化");
        System.out.println("--------------------");
        System.out.println("开启会话，Hibernate自动建表");
        System.out.println("执行SQL文件，插入数据");
        startTime = System.currentTimeMillis();
        init();
        printTime();

        /*
        * 用户(13218019068，陈骁，话费余额400元)订购套餐
        * 订购的是以下4个套餐
        * "话费包月套餐(ID=1)": 价格为20元，优惠为100分钟免费通话时长
        * "短信包月套餐(ID=2)": 价格为10元，优惠为200条免费短信
        * "本地流量包月套餐(ID=3)": 价格为20元，优惠为100分钟免费通话时长
        * "国内流量包月套餐(ID=4)": 价格为30元，优惠为200条免费短信
        *
        * 对于每一次套餐订阅
        * 在orders表中：
        *   如果用户没有订阅套餐，则插入一条新的记录，保存操作时间和支付金额；
        *   如果用户正在适用该套餐，则会提示不能订阅
        *   (用户对于退订过的套餐可以再次订阅，这会在orders表中留下两条记录；
        *   对于月底生效的退订，用户在本月再次订阅该套餐时，会提示不能订阅)
        * 在plan_record表中：插入一条记录，记录订阅时间和数额
        * */
        try {
            System.out.println("#1.订购套餐: ");
            System.out.println("--------------------");

            User user = session.load(User.class, "13218019068");
            User callee = session.load(User.class, "13780946508");
            System.out.println("## 订购前的用户信息");
            System.out.println();
            user.showInfo();
            System.out.println();
            System.out.println();

            startTime = System.currentTimeMillis();
            Plan plan1 = session.load(Plan.class, (long)1);
            Plan plan2 = session.load(Plan.class, (long)2);
            Plan plan3 = session.load(Plan.class, (long)3);
            Plan plan4 = session.load(Plan.class, (long)4);
            planService.orderPlan(user, plan1, true);
            planService.orderPlan(user, plan2, true);
            planService.orderPlan(user, plan3, true);
            planService.orderPlan(user, plan4, true);
            planService.orderPlan(callee, plan4, true);

            System.out.println("## 订购后的用户信息");
            System.out.println();
            user.showInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 用户(13218019068，陈骁，话费余额400元)查询订购的套餐并打印
         * 订购的是上一步中的4个套餐
         * 打印套餐名、套餐价格、套餐内容
         * */
        try {
            System.out.println("#2.查询套餐: ");
            System.out.println("--------------------");
            startTime = System.currentTimeMillis();
            User user = session.load(User.class, "13218019068");
            List<Plan> planList = planService.searchPlan(user);
            for(int i = 0; i < planList.size(); i++) {
                if (i != 0) {
                    System.out.println();
                }
                planList.get(i).showInfo();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 用户(13218019068，陈骁)向用户(13780946508，刘嘉)打电话
         * 通话时长为20分钟，只会消耗用户陈骁的免费通话时长，
         * 打印通话信息，包括通话时间、免费通话时长、付费通话时长、扣除的话费等
         * */
        try {
            System.out.println("#3.拨打电话: ");
            System.out.println("--------------------");

            startTime = System.currentTimeMillis();
            User caller = session.load(User.class, "13218019068");
            User callee = session.load(User.class, "13780946508");
            if (caller.getBalance() > 0) {
                CallRecord record = new CallRecord(
                        TimeTool.now(), (long)20, caller.getPhone(), callee.getPhone());
                recordService.addCallRecord(record, caller);
                record.showInfo();
            } else {
                throw new BalanceException("错误: 无法呼叫用户余额为负数");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 用户(13218019068，陈骁)使用 2075M 本地流量
         * 超出了套餐内的优惠 2000M，额外的 75M 消耗用户余额
         * 打印流量使用信息，包括使用时间、免费流量、付费流量、扣除的话费等
         * */
        try {
            System.out.println("#4.使用本地流量: ");
            System.out.println("--------------------");

            startTime = System.currentTimeMillis();
            User user = session.load(User.class, "13218019068");
            LocalDataRecord record = new LocalDataRecord(TimeTool.now(), 2075.0, user.getPhone());
            recordService.addLocalDataRecord(record, user);
            record.showInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 用户(13218019068，陈骁，话费余额400元)退订套餐1，立即生效
         * 套餐信息: "话费包月套餐(ID=1)"，价格为20元，优惠为100分钟免费通话时长
         *
         * 对于立即生效的套餐退订，计算用户已经使用的优惠，对于剩余的优惠进行退款
         * 用户已经拨打了20分钟电话，所以退款的金额为 (100-20)/100 * 20 = 16元
         * 用户原来有170元的话费和剩余80分钟的免费通话时长
         * 退订后应该有186元的话费和0分钟的免费通话时长
         *
         * 在orders表中：设置订阅结束日期为现在，设置订阅状态为"不订阅"，续约状态为"不续约"
         * 在plan_record表中：插入一条记录，记录退订时间和数额
         *
         * 退订完成后打印用户信息，显示用户的话费和免费通话时长确实如上文所说
         * 并且查询套餐，会看到"话费包月套餐"的订阅记录更新
         * */
        try {
            System.out.println("#5.退订\"话费包月套餐\"立即生效: ");
            System.out.println("--------------------");

            startTime = System.currentTimeMillis();
            User user = session.load(User.class, "13218019068");
            Plan plan = session.load(Plan.class, (long)1);
            planService.cancelPlan(user, plan, true);

            System.out.println("## 退订后的用户信息");
            System.out.println();
            user.showInfo();

            System.out.println();
            System.out.println();
            System.out.println("## 用户的订阅记录");
            System.out.println();

            plan.showInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 用户(13218019068，陈骁，话费余额400元)退订套餐2，月底生效
         * 套餐信息: "短信包月套餐(ID=2)"，价格为10元，优惠为200条免费短信
         *
         * 对于月底生效的套餐退订，不会退款，本月仍可以使用套餐优惠
         * 在orders表中：设置订阅结束日期为本月最后一天，修改续约状态；但是不改变订阅状态，不退款
         * 在plan_record表中：插入一条记录，记录退订时间和数额（数额为0）
         *
         * 在每个月第一天的0点，系统扫描所有订阅记录，
         * 对于续约状态为"不续约"的套餐订阅，将订阅状态改成"不订阅"
         * 对于续约状态为"续约中"的套餐订阅，插入一条
         *
         * 退订完成后打印用户信息，显示用户的话费和免费短信数量没有变化
         * 并且查询套餐，会看到"短信包月套餐"的订阅记录更新，结束日期为本月底，续约状态为"不续约"
         * */
        try {
            System.out.println("#6.退订\"话费包月套餐\" 月底生效: ");
            System.out.println("--------------------");

            startTime = System.currentTimeMillis();
            User user = session.load(User.class, "13218019068");
            Plan plan = session.load(Plan.class, (long)2);
            planService.cancelPlan(user, plan, false);

            System.out.println("## 退订后的用户信息");
            System.out.println();
            user.showInfo();

            System.out.println();
            System.out.println();
            System.out.println("## 用户的订阅记录");
            System.out.println();

            plan.showInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        /*
         * 打印用户(13218019068，陈骁，话费余额400元)的账单
         * 可以设置参数查找不同月份，代码中打印的是本月的账单
         *
         * 通过编写配置文件，使得每次开启会话时hibernate都会重新创建表
         * 随后执行sql语句，插入演示用的数据
         * 所以打印的账单结果应该仅限于上面的这些操作
         * */
        try {
            System.out.println("#7 账单查询: ");
            System.out.println("--------------------");
            User user = session.load(User.class, "13218019068");
            userService.checkBill(TimeTool.getYear(), TimeTool.getMonth(), user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            printTime();
        }

        destroy();
    }

    // 初始化数据库连接工具，并加载从sql文件插入数据
    // hibernate会在开启会话时自动创建数据库
    private static void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        planService = new PlanService(session, transaction);
        recordService = new RecordService(session, transaction);
        userService = new UserService(session, transaction);

        ArrayList<String> sqlList = FileTool.readFile("src/main/resources/sql/homework_2.sql");
        for(String sql: sqlList) {
            session.createSQLQuery(sql).executeUpdate();
        }
    }

    private static void printTime() {
        endTime = System.currentTimeMillis();
        System.out.println("--------------------");
        System.out.println("Time: " + (endTime - startTime) + "ms");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public static void destroy() {
        session.close(); //关闭会话
        sessionFactory.close(); //关闭会话工厂
    }
}
