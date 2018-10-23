package service;

import entity.*;
import exception.BalanceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.Arith;

public class RecordService extends Service{

    public RecordService(Session session, Transaction transaction) {
        super(session, transaction);
    }

    public void addCallRecord(CallRecord record, User user) throws Exception{
        openTransaction();
        long minutes = record.getMinutes();
        long freeMinutes = user.deductFreeCallMinutes(minutes);
        long paidMinutes = minutes - freeMinutes;

        double callPrice = session.load(BasicPrice.class, (long)0).getCallPrice();
        double callExpense = Arith.mul(paidMinutes, callPrice);
        user.deductBalance(callExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为" + user.getBalance() + "元");
        }
    }

    public void addMessageRecord(MessageRecord record, User user) throws Exception{
        openTransaction();

        long messageNum = record.getNumber();
        long freeMessageNum = user.deductFreeMessageNum((long)messageNum);
        long paidMessageNum = messageNum - freeMessageNum;

        double messagePrice = session.load(BasicPrice.class, (long)0).getMessagePrice();
        double messageExpense = Arith.mul((double)paidMessageNum, messagePrice);
        user.deductBalance(messageExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为" + user.getBalance() + "元");
        }
    }

    public void addLocalDataRecord(LocalDataRecord record, User user) throws Exception{
        openTransaction();

        double amount = record.getAmount();
        double freeAmount = user.deductFreeLocalData(amount);
        double paidAmount = amount - freeAmount;

        double localDataPrice = session.load(BasicPrice.class, (long)0).getLocalDataPrice();
        double localDataExpense = Arith.mul(paidAmount, localDataPrice);
        user.deductBalance(localDataExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为" + user.getBalance() + "元");
        }
    }

    public void addDomesticDataRecord(DomesticDataRecord record, User user) throws Exception{
        openTransaction();

        double amount = record.getAmount();
        double freeAmount = user.deductFreeDomesticData(amount);
        double paidAmount = amount - freeAmount;

        double domesticDataPrice = session.load(BasicPrice.class, (long)0).getDomesticDataPrice();
        double domesticDataExpense = Arith.mul(paidAmount, domesticDataPrice);
        user.deductBalance(domesticDataExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为" + user.getBalance() + "元");
        }
    }
}
