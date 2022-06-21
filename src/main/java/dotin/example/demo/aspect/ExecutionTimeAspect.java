package dotin.example.demo.aspect;

import dotin.example.demo.exception.ResourceNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Aspect
@Configuration
public class ExecutionTimeAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("dotin.example.demo.aspect.JoinPointConfig.logExecutionTimePointcut()")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            if (e instanceof ResourceNotFoundException) {
                logger.error(e.getMessage());
            }
            throw e;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken by methhod is " + executionTime);
        logger.info("Time taken by methhod is " + executionTime);
        return result;

    }

    @Around("dotin.example.demo.aspect.JoinPointConfig.logAfterReturningExecution()")
    public Object logAfterReturningExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity) {
                System.out.println(((ResponseEntity<?>) result).getStatusCode());
            }
        } catch (Throwable e) {
            if (e instanceof ResourceNotFoundException) {
                logger.error(e.getMessage());
            }
            throw e;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken by methhod is " + executionTime);
        logger.info("Time taken by methhod is " + executionTime);
        return result;

    }
}
