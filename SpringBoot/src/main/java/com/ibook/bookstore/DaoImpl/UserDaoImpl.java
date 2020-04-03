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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findOne(String name) {
        return userRepository.getOne(name);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByEmail(String email) {return userRepository.findByEmail(email);}

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(String name) {
        userRepository.deleteByName(name);
    }

    @Override
    public User AddAddress(Address address, User user) {
        user.getAddresses().add(address);
        return userRepository.save(user);
    }
}
