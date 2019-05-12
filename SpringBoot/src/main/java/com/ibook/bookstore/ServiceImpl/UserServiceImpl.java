package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUserByName(String name) {
        return  userDao.findOne(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Page<User> findUserByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDao.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        user.setState(false);
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        user.setAvatar(user.getAvatar() == null ? "http://localhost:8080/img?kind=user&name=default" : user.getAvatar());
        return userDao.saveUser(user);
    }

    @Override
    public void deleteUser(String name) {
        userDao.deleteUser(name);
    }

    /*
     * Basic information updated here, address / liked books updated else where
     */
    @Override
    public User updateUser(Map<String, String> data) {
        String name = data.get("name");
        User user = userDao.findOne(name);
        String email = data.get("email"), password = data.get("password"),
               state = data.get("state");
        if (email != null)
            user.setEmail(email);
        if (password != null)
            user.setPassword(password);
        if (state != null)
            user.setState(Boolean.parseBoolean(state));
        user.setModifyDate(new Timestamp(System.currentTimeMillis()));
        return userDao.saveUser(user);
    }


}
