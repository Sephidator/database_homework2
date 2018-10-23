package service;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;


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
}
