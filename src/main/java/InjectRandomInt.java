import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by slava23 on 2/15/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
