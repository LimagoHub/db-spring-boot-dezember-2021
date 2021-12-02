package de.db.webapp.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(public * de.db.webapp.controllers.*Controller.*(..)))")
    public void controllerMethods(){}

    @Pointcut(value = "within(@org.springframework.web.bind.annotation.RestController *)" )
    public void restControllerMethodes() {}

    @Pointcut(value = "within(@org.springframework.stereotype.Service *)")
    public void serviceMethods(){}
}
