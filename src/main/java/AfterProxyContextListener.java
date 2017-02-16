import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by slava23 on 2/16/2017.
 */
public class AfterProxyContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames)
                .stream()
                .forEach(beanDefinitionName -> {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                    String beanClassName = beanDefinition.getBeanClassName();
                    try {
                        Class<?> originalClass = Class.forName(beanClassName);
                        Method[] methods = originalClass.getMethods();
                        Arrays.asList(methods)
                                .stream()
                                .forEach(method -> {
                                    if (method.isAnnotationPresent(AfterProxy.class)) {
                                        Object bean = context.getBean(beanDefinitionName);
                                        try {
                                            Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                                            currentMethod.invoke(bean);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
