import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * Created by slava23 on 2/15/2017.
 */
@Profiling
public class QuoteGenerator implements Generator {

    @Value("${app.message}")
    private String message;
    @InjectRandomInt(min = 2, max = 5)
    private int repeat;

    public QuoteGenerator() {
        System.out.println("Phase 1..");
    }

    public void sayQuote() {
        for (int i= 0; i< repeat; i++) {
            System.out.println(message);
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2..");
        System.out.println("Repeat = " + repeat);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
