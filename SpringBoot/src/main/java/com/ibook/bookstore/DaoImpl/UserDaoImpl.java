package com.ibook.bookstore.DaoImpl;

import com.google.common.base.Optional;
import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(String name) {
        return userRepository.getOne(name);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Optional<User> findByEmail(String email) {return userRepository.findByEmail(email);}

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String name) {
        userRepository.deleteByName(name);
    }

    @Override
    public User AddAddress(Address address, User user) {
        user.getAddresses().add(address);
        return userRepository.save(user);
    }
}
