package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDao;
import com.ibook.bookstore.DaoImpl.BookDaoImpl;
import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Service.RemoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Service
public class RemoteBookServiceImpl extends UnicastRemoteObject implements RemoteBookService {
    @Autowired
    private BookDao bookDao;

    public RemoteBookServiceImpl() throws RemoteException {
    }

    @Override
    public Book findBookById(String id) throws RemoteException {
        return  bookDao.findOne(id);
    }
}
