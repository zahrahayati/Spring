package dotin.example.demo.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfig {
    @Pointcut("@annotation(dotin.example.demo.aspect.ExecutionTime)")
    public void logExecutionTimePointcut() {
    }

    @Pointcut("execution(* dotin.example.demo.controller.*.*(..))")
    public void logAfterReturningExecution() {
    }

    @Pointcut("@annotation(dotin.example.demo.aspect.ActivityLogAnnotation)")
    public void afterMethodExecution() {
    }
}
