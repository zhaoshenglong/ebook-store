package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUserByName(String name) {
        return  userDao.findOne(name);
    }
}
