package com.ibook.bookstore;

import com.ibook.bookstore.Service.RemoteBookService;
import com.ibook.bookstore.ServiceImpl.RemoteBookServiceImpl;
import org.apache.catalina.core.ApplicationContext;
import org.hibernate.id.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BookstoreApplication.class, args);

        // register rmi srv
        try {
            RemoteBookService remoteBookService = applicationContext.getBean(RemoteBookService.class);
            Context namingContext = new InitialContext();
            namingContext.bind("rmi:book_srv", remoteBookService);
            System.out.println("Bind ok");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
