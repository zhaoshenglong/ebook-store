package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findOne(String name) {
        return userRepository.getOne(name);
    }
}
