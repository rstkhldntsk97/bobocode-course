package com.bobocode;

import com.bobocode.annotation.EnableStringTrimming;
import com.bobocode.service.BadAssService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableStringTrimming
public class TrimmingApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.bobocode");
        BadAssService service = context.getBean(BadAssService.class);
        System.out.println(service.greeting("I'm a bad man"));
        System.out.println(service.greeting("         Bad man"));
        System.out.println(service.greeting("         Batman          "));
    }

}
