package com.ibook.bookstore.DaoImpl;

import com.ibook.bookstore.Dao.PersonDao;
import com.ibook.bookstore.Entity.Person;
import com.ibook.bookstore.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findOne(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}
