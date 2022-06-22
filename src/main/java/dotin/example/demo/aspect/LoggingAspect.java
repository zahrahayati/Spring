package dotin.example.demo.aspect;

import dotin.example.demo.model.ActivityLog;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.repository.ActivityLogJDBCTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;


@Aspect
@Configuration
public class LoggingAspect {

    @Autowired
    private ActivityLogJDBCTemplate activityLogJDBCTemplate;

    @Autowired
    private ActivityLog activityLog;

    @AfterReturning(value = "dotin.example.demo.aspect.JoinPointConfig.afterMethodExecution()", returning = "result")
    public void afterMethodExecution(JoinPoint joinPoint) {
        Optional request = Arrays.stream(joinPoint.getArgs()).findFirst();
        if(request != null) {
            CardPrintRequest cardPrintRequest = (CardPrintRequest) request.get();
            activityLog.setCardPAN(cardPrintRequest.getCardPAN());
            activityLog.setPersonnelCode(cardPrintRequest.getPersonnelCode());
        }
        activityLog.setActionName(joinPoint.getSignature().getName());
        activityLog.setActionDate(new java.sql.Date(System.currentTimeMillis()));
        activityLogJDBCTemplate.save(activityLog);

    }
}
