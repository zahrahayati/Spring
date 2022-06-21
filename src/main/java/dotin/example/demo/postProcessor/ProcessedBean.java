package dotin.example.demo.postProcessor;

import org.springframework.context.ApplicationContext;

public interface ProcessedBean {
    void setApplicationContext(ApplicationContext applicationContext);
}
