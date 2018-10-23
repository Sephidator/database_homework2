package exception;

// 余额错误
// 当用户余额不足的情况时抛出
public class BalanceException extends Exception {

    // 无参构造方法
    public BalanceException(){
        super("余额不足");
    }

    // 有参构造方法
    public BalanceException(String message){
        super(message);
    }
}