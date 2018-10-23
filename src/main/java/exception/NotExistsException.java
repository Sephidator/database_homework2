package exception;

// 不存在错误
// 当用户退订套餐，而套餐不存在时抛出
public class NotExistsException extends Exception {

    // 无参构造方法
    public NotExistsException(){
        super("已存在");
    }

    // 有参的构造方法
    public NotExistsException(String message){
        super(message);
    }
}