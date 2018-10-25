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

    public void addCallRecord(CallRecord record, User user) throws BalanceException {
        openTransaction();
        long minutes = record.getMinutes();
        long freeMinutes = user.deductFreeCallMinutes(minutes);
        long paidMinutes = minutes - freeMinutes;
        double callPrice = priceInfo().getCallPrice();
        double callExpense = Arith.mul(paidMinutes, callPrice);

        record.setFreeMinutes(freeMinutes);
        record.setPaidMinutes(paidMinutes);
        record.setExpense(callExpense);
        user.deductBalance(callExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为"
                    + Arith.format(user.getBalance()) + "元");
        }
    }

    public void addMessageRecord(MessageRecord record, User user) throws BalanceException {
        openTransaction();

        long messageNum = record.getNumber();
        long freeMessageNum = user.deductFreeMessageNum((long)messageNum);
        long paidMessageNum = messageNum - freeMessageNum;
        double messagePrice = priceInfo().getMessagePrice();
        double messageExpense = Arith.mul((double)paidMessageNum, messagePrice);

        record.setFreeNum(freeMessageNum);
        record.setPaidNum(paidMessageNum);
        record.setExpense(messageExpense);
        user.deductBalance(messageExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为"
                    + Arith.format(user.getBalance()) + "元");
        }
    }

    public void addLocalDataRecord(LocalDataRecord record, User user) throws BalanceException {
        openTransaction();

        double amount = record.getAmount();
        double freeAmount = user.deductFreeLocalData(amount);
        double paidAmount = amount - freeAmount;
        double localDataPrice = priceInfo().getLocalDataPrice();
        double localDataExpense = Arith.mul(paidAmount, localDataPrice);

        record.setFreeAmount(freeAmount);
        record.setPaidAmount(paidAmount);
        record.setExpense(localDataExpense);
        user.deductBalance(localDataExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为"
                    + Arith.format(user.getBalance()) + "元");
        }
    }

    public void addDomesticDataRecord(DomesticDataRecord record, User user) throws BalanceException {
        openTransaction();

        double amount = record.getAmount();
        double freeAmount = user.deductFreeDomesticData(amount);
        double paidAmount = amount - freeAmount;
        double domesticDataPrice = priceInfo().getDomesticDataPrice();
        double domesticDataExpense = Arith.mul(paidAmount, domesticDataPrice);

        record.setFreeAmount(freeAmount);
        record.setPaidAmount(paidAmount);
        record.setExpense(domesticDataExpense);
        user.deductBalance(domesticDataExpense);

        session.save(user);
        session.save(record);
        transaction.commit();

        if (user.getBalance() < 0) {
            throw new BalanceException("警告：用户余额不足，余额为"
                    + Arith.format(user.getBalance()) + "元");
        }
    }
}
