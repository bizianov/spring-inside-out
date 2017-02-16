import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by slava23 on 2/15/2017.
 */

public class Launcher {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
    }
}
