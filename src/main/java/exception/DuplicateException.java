package exception;

// 重复错误
// 当用户订阅已订阅的套餐、取消已取消的套餐等"重复"发生时抛出
public class DuplicateException extends Exception {

    // 无参构造方法
    public DuplicateException(){
        super("已存在");
    }

    // 有参的构造方法
    public DuplicateException(String message){
        super(message);
    }
}