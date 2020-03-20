package com.ibook.bookstore;

import com.ibook.bookstore.Service.RemoteBookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BookstoreApplication.class, args);

        // register rmi srv
        try {
            Class cls = Class.forName("org.springframework.boot.SpringApplication");
            ClassLoader cLoader = cls.getClassLoader();
            if (cLoader == null) {
                System.out.println("The default system class was used");
            } else {
                Class loaderClass = cLoader.getClass();
                System.out.println("Class associated with ClassLoader = " + loaderClass.getName());
            }
            RemoteBookService remoteBookService = applicationContext.getBean(RemoteBookService.class);
            Context namingContext = new InitialContext();
            namingContext.bind("rmi:book_srv", remoteBookService);
            System.out.println("Bind ok");
        } catch (NamingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
