package de.db.webapp.aspects;

import de.db.webapp.controllers.dtos.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {



    @Before(value = "Pointcuts.controllerMethods()")
    public void myBeforeAdvice(JoinPoint joinPoint) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "Pointcuts.controllerMethods()", returning = "result")
    public void myAfterReturningAdvice(JoinPoint joinPoint, Object result) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
        log.warn("Klasse {}", joinPoint.getTarget().getClass().getName());
        log.warn("Result ist {}.", result.toString());
    }

    @AfterThrowing(value = "Pointcuts.restControllerMethodes()", throwing = "ex")
    public void myAfterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
        log.warn("Methode {} wurde gerufen", joinPoint.getSignature().getName());
        log.warn("Klasse {}", joinPoint.getTarget().getClass().getName());
        log.warn("Message ist {}.", ex.getMessage());
    }

    @Around(value = "execution(public * de.db.webapp.controllers.*Controller.*(..))")
    public Object zeitMessungAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
       return proceedingJoinPoint.proceed();
    }

    @Pointcut(value = "execution(public * de.db.webapp.controllers.PersonenQueryController.*(..)) && args(personDTO,..)")
    private void savePerson(PersonDTO personDTO) {}

    @Before("savePerson(personDTO)")
    public void validateAccount(PersonDTO personDTO) {
        // ...
    }

    @AfterThrowing(value = "Pointcuts.serviceMethods()", throwing = "ex")
    public void afterThrowingInServices(JoinPoint joinPoint, Throwable ex) {
        log.error("Upps {}", "abc", ex);
    }
}
