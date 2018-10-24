package service;

import entity.BasicPrice;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class Service {
    protected Session session;
    protected Transaction transaction;

    private Service() {}

    protected Service(Session session, Transaction transaction) {
        this.session = session;
        this.transaction = transaction;
    }

    protected void openTransaction() {
        if (!this.transaction.isActive()) {
            this.transaction = session.beginTransaction();
        }
    }

    /**
     * 从数据库读取基础价格信息。
     * @return 最新（ID最大）的基础价格信息
     */
    protected BasicPrice priceInfo() {
        String hql = "FROM BasicPrice ORDER BY pid DESC";
        Query<BasicPrice> query = session.createQuery(hql).setMaxResults(1);
        return query.list().get(0);
    }
}
