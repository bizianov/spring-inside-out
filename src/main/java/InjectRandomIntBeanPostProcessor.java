import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by slava23 on 2/15/2017.
 */
public class InjectRandomIntBeanPostProcessor implements BeanPostProcessor{
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        Arrays.asList(fields)
                .stream()
                .forEach(field -> {
                    InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
                    if (annotation != null) {
                        int min = annotation.min();
                        int max = annotation.max();
                        Random random = new Random();
                        int result = min + random.nextInt(max - min);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, bean, result);
                    }
                });
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
