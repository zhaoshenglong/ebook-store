package com.ibook.bookstore.Service;

import com.ibook.bookstore.Entity.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteBookService extends Remote {
    Book findBookById(String id) throws RemoteException;
}
