package dotin.example.demo.postProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessorSample implements BeanPostProcessor {
    private static Log log = LogFactory.getLog(BeanPostProcessorSample.class);

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Scope annotation = AnnotationUtils.getAnnotation(bean.getClass(), Scope.class);
        if (annotation != null) {
            if (annotation.value().equals(ConfigurableBeanFactory.SCOPE_SINGLETON)) {
                log.info("singleton bean name is : " + beanName);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ProcessedBean) {
            ProcessedBean processedBean = (ProcessedBean) bean;
            processedBean.setApplicationContext(this.applicationContext);
            log.info("setApplicationContext for Bean instanceof ProcessedBean ");
        }

        return bean;
    }
}
