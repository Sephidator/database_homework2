package exception;

// 数据库错误
// 当数据库内部数据有问题的情况时抛出
// 比如orders表中，用户对一个套餐有两条"正在生效中"的记录
public class DatabaseException extends Exception {

    // 无参构造方法
    public DatabaseException(){
        super("数据库错误");
    }

    // 有参构造方法
    public DatabaseException(String message){
        super(message);
    }
}