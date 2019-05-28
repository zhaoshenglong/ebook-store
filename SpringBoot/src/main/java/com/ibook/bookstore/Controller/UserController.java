package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Address;
import com.ibook.bookstore.Entity.User;
import com.ibook.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/api/test/user/{name}")
    public User getUserByName(@PathVariable("name") String name) {
        return userService.findUserByName(name);
    }

    @GetMapping("/api/user/{name}/password/verify")
    public String verifyPassword(@PathVariable("name")String name,
                                  @RequestParam("password")String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        return userService.verifyPassword(request.getSession(), password, response);
    }

    @PutMapping("/api/user/{name}/modify")
    public User updateUser(@PathVariable("name") String name,
                           @RequestBody Map<String, String> data) {
        return userService.updateUser(data);
    }

    @PostMapping("/api/public/register")
    public User register(@RequestBody User user) {
        System.out.println(user);
        return userService.createUser(user);
    }

    @GetMapping("/api/public/status")
    public Map<String, String>getStatus(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return userService.getStatus(session, response);
    }
    @PostMapping("/api/user/{name}/addresses/add")
    public User addAddress(@PathVariable("name") String name, @RequestBody Address address){
        return userService.addAddress(name, address);
    }

    @PutMapping("/api/user/{name}/addresses/modify")
    public User updateAddress(@PathVariable("name")String name, @RequestBody Map<String, String> addressData) {
        System.out.println(name);
        return userService.updateAddress(addressData);
    }

    @DeleteMapping("/api/user/{name}/addresses/delete")
    public void deleteAddress(@PathVariable("name")String name,
                              @RequestParam("id") String id){
        userService.deleteAddress(id);
    }

    @PostMapping("/api/user/{name}/avatars/upload")
    public void uploadAvatar(@RequestParam("avatar")MultipartFile avatar,
                             @PathVariable("name")String name,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        userService.uploadAvatar(avatar, name, session);
    }

    /**
     * Admin manage controller, get / manage / delete users
     */

     @GetMapping("/api/admin/users/all")
    public Page<User> findUserByPage(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return userService.findUserByPage(page, 10);
    }

    @DeleteMapping("/api/admin/users/delete")
    public void deleteUser(@RequestParam(name = "name")String name) {
        userService.deleteUser(name);
    }

    @PutMapping("/api/admin/users/modify")
    public User updateUser(@RequestBody Map<String ,String> data) {
        return userService.updateUser(data);
    }

}
