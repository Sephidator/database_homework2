import org.junit.Test;

import java.util.Date;

public class MainTest {

    @Test
    public void test1() {
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(new Date());
    }
}