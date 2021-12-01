package de.db.webapp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {

    @Before(value = "execution(public * de.db.webapp.controllers.*Controller.*(..))")
    public void myBeforeAdvice(JoinPoint joinPoint) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(public * de.db.webapp.controllers.*Controller.*(..))", returning = "result")
    public void myAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
        log.warn("Klasse {}", joinPoint.getTarget().getClass().getName());
        log.warn("Result ist {}.", result.toString());
    }

    @AfterThrowing(value = "execution(public * de.db.webapp.controllers.*Controller.*(..))", throwing = "ex")
    public void myAfterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
        log.warn("Klasse {}", joinPoint.getTarget().getClass().getName());
        log.warn("Message ist {}.", ex.getMessage());
    }

    @Around(value = "execution(public * de.db.webapp.controllers.*Controller.*(..))")
    public Object zeitMessungAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
       return proceedingJoinPoint.proceed();
    }
}
