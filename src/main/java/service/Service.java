package service;

import org.hibernate.Session;
import org.hibernate.Transaction;


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

}
