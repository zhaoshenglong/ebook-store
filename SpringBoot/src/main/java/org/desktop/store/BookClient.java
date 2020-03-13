package org.desktop.store;

import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Service.RemoteBookService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class BookClient {
    public BookClient() {
    }


    public static void main(String[] args) throws NamingException, RemoteException {
        Context namingContext = new InitialContext();
        String url = "rmi://localhost/book_srv";
        RemoteBookService remoteBookService = (RemoteBookService) namingContext.lookup(url);
        String id = "ff80818170c5314c0170c53212bb0001";
        Book book = remoteBookService.findBookById(id);
        System.out.println(book);
    }
}
