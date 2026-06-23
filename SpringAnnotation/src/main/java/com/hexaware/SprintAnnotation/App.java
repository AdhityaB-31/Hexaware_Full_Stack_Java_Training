package com.hexaware.SprintAnnotation;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.hexaware.SprintAnnotation.bean.*;
import com.hexaware.SprintAnnotation.bean.User;

/**
 * Hello world!
 *
 */

@Configuration
@ComponentScans(value = { @ComponentScan(basePackages = "com.hexaware.SprintAnnotation.*") })
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

		User u1 = context.getBean(User.class);

		u1.setFullName("Adhitya");

		System.out.println(u1);

		System.out.println(u1.getFullName());

		Bus b1 = context.getBean(Bus.class);

		b1.setBusId(101);
		b1.setBusName("Kanimozhi");
		b1.setBusType("Sleeper AC");
		// e1.setAddress(a1);

		System.out.println(b1);
		System.out.println(b1.getBusType());

		System.out.println(b1.getBusName());

		System.out.println(b1.getBusModel());

		Bus b2 = context.getBean(Bus.class);
		System.out.println(b2);
		
		Thread t1 =	context.getBean("tr1", Thread.class);
			System.out.println(t1);
			
			Thread t2 =	context.getBean( Thread.class);
			System.out.println(t2);
		

	}
	
	
	
	@Bean("tr1")
	@Scope("prototype")
	public Thread   getThreadObj() {
		
		
		return  new Thread();
		
	}
	
	
	
	
	
	
	
	
	
}

