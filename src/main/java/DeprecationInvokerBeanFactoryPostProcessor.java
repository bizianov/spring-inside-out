import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;

/**
 * Created by slava23 on 2/16/2017.
 */
public class DeprecationInvokerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames)
                .stream()
                .forEach(beanDefinitionName -> {
                    BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
                    String beanClassName = beanDefinition.getBeanClassName();
                    try {
                        Class<?> beanClass = Class.forName(beanClassName);
                        DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                        if (annotation != null) {
                            Class newImplClass = annotation.newImpl();
                            beanDefinition.setBeanClassName(newImplClass.getName());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }
}
