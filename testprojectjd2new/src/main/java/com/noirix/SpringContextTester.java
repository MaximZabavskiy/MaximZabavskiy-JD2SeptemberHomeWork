package com.noirix;

import com.noirix.domain.Car;
import com.noirix.domain.User;
import com.noirix.repository.UserRepository;
import com.noirix.util.DatabaseConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Collectors;

public class SpringContextTester {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");

        User user1 = (User) classPathXmlApplicationContext.getBean("user1");
        User user2 = (User) classPathXmlApplicationContext.getBean("user2");

        System.out.println(user1.getId());
        System.out.println(user1.getName());
        System.out.println(user1.getSurname());

        System.out.println(user2.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getSurname());


        System.out.println("******************************************");

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.noirix");

        DatabaseConfig bean = annotationConfigApplicationContext.getBean(DatabaseConfig.class);
        System.out.println(bean.getLogin());
        System.out.println(bean.getDriverName());
        System.out.println(bean.getPassword());
        System.out.println(bean.getUrl());
        /* This is how to check available beans in context */

//        for (String beanDefinitionName : annotationConfigApplicationContext.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }

        Car generatedCar = annotationConfigApplicationContext.getBean(Car.class);
        System.out.println(generatedCar);


        UserRepository userRepository = annotationConfigApplicationContext.getBean(UserRepository.class);

        System.out.println(userRepository.findAll().stream().map(User::getName).collect(Collectors.joining(", ")));
    }
}
