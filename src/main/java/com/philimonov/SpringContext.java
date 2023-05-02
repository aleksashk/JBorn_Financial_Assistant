package com.philimonov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        if (context == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ignore) {
            }
            context = new AnnotationConfigApplicationContext("com.philimonov");
        }
        return context;
    }
}
