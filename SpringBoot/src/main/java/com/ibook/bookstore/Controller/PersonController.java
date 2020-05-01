package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Person;
import com.ibook.bookstore.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.Set;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/api/user/{name}/followers")
    public Set<Person> getFollowers(@PathVariable("name") String name) {
        return personService.findFollowers(name);
    }

    @GetMapping("/api/user/{name}/followings")
    public Set<Person> getFollowings(@PathVariable("name") String name) {
        return personService.findFollowings(name);
    }

    @PostMapping("/api/user/follow")
    public Person follow(@RequestParam String userName,
                         @RequestParam String targetName) {
        return personService.follow(userName, targetName);
    }

    @PostMapping("/api/user/unfollow")
    public Person unFollow(@RequestParam String userName,
                           @RequestParam String targetName) {
        return personService.unFollow(userName, targetName);
    }
}
