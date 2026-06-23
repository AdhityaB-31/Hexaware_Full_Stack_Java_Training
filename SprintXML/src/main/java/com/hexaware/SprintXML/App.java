package com.hexaware.SprintXML;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hexaware.SprintXML.bean.*;

public class App {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("ApplicationContext.xml");

        User user = (User) context.getBean("user1");
        Bus bus = (Bus) context.getBean("bus1");

        System.out.println(user);
        System.out.println(bus);
    }
}