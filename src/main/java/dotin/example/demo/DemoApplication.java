package dotin.example.demo;

import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.model.CardPrintRequestEmbeddedId;
import dotin.example.demo.repository.CardPrintRequestJDBCTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

    @Autowired
    private CardPrintRequestJDBCTemplate cardPrintRequestJDBCTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("StartApplication...");
        runJDBC();
    }

    void runJDBC() throws ParseException {
        List<CardPrintRequest> cardPrintRequests = Arrays.asList(
                new CardPrintRequest(new CardPrintRequestEmbeddedId("600", "10.20.152.15"), "6063731098404032", "74100"),
                new CardPrintRequest(new CardPrintRequestEmbeddedId("6071", "10.20.152.18"), "6063731098404055", "75120"),
                new CardPrintRequest(new CardPrintRequestEmbeddedId("6070", "10.20.152.18"), "6063731098404055", "74501")
        );
        cardPrintRequests.forEach(cardPrintRequest -> {
            log.info("Saving...{}");
            cardPrintRequestJDBCTemplate.save(cardPrintRequest);
        });
    }
    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the singleton beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                if(ctx.isSingleton(beanName))
                    log.info("singleton Bean Name is: "+ beanName);
            }
        };
    }

     */
}
