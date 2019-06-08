package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.UserDetailDao;
import com.ibook.bookstore.Entity.UserDetail;
import com.ibook.bookstore.Repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailDaoImpl implements UserDetailDao {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetail findOne(String name) {
        return userDetailRepository.findByName(name);
    }

    @Override
    public UserDetail saveOne(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }
}
