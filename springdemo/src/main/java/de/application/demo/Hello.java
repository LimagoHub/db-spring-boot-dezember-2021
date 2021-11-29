package de.application.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component // stellt Komponente unter Verwaltung von Spring
//@Scope(BeanDefinition.SCOPE_SINGLETON)
//@Lazy
public class Hello implements AutoCloseable{


    private String message = "Hallo Welt";

    private final Transformer transformer;


//    public void setTransformer(Transformer transformer) {
//        this.transformer = transformer;
//    }

//    public Hello() {
//
//        System.out.println("Hier ist Hello");
//        System.out.println(message);
//    }

    @Autowired // löst Abhängigkeiten automatisch auf (Vor. alle Komponenten stehen unter der Verwaltung von Spring)
    public Hello(@Qualifier("upper") final Transformer transformer) {
        this.transformer = transformer;
        System.out.println("Hier ist Hello");
        System.out.println(transformer.transform(message));
    }

    public String getMessage() {
        return message;
    }

    @Value("Hello Universe")
    public void setMessage(String message) {
        this.message = message;
    }

    @PostConstruct // init-method
    public void init() {
        System.out.println("Post Construct");
        System.out.println(transformer.transform(message));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hello{");
        sb.append("message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @PreDestroy
    @Override
    public void close() throws Exception {
        System.out.println(transformer.transform("Und tschuess!"));
    }
}
