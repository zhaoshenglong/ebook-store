package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.PersonDao;
import com.ibook.bookstore.Entity.Person;
import com.ibook.bookstore.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    public Set<Person> findFollowers(String name) {
        return personDao.findOne(name).getFollowers();
    }

    @Override
    public Set<Person> findFollowings(String name) {
        return personDao.findOne(name).getFollowings();
    }

    @Override
    public Person follow(String userName, String targetName) {
        Person person = personDao.findOne(userName),
                targetPerson = personDao.findOne(targetName);
        person.getFollowings().add(targetPerson);
        targetPerson.getFollowers().add(person);
        person = personDao.savePerson(person);
        personDao.savePerson(targetPerson);
        return person;
    }

    @Override
    public Person unFollow(String userName, String targetName) {
        Person person = personDao.findOne(userName),
                targetPerson = personDao.findOne(targetName);
        for (Person p: person.getFollowings()) {
            if (p.getName().equals(targetName)) {
                person.getFollowings().remove(p);
                break;
            }
        }
        for (Person p: targetPerson.getFollowers()) {
            if (p.getName().equals(userName)) {
                targetPerson.getFollowers().remove(p);
                break;
            }
        }
        person = personDao.savePerson(person);
        personDao.savePerson(targetPerson);
        return person;
    }
}
