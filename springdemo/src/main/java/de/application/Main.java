package de.application;

import de.application.demo.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




@ComponentScan("de.application")
public class Main {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		//final AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		final AbstractApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		context.registerShutdownHook();

		Hello hello = context.getBean(Hello.class);

		System.out.println(hello.getMessage());


	}

}
