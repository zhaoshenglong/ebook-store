package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] displayImage(@RequestParam("kind")String kind, @RequestParam("name")String name) {
        return pictureService.loadImage(kind, name);
    }

    @PostMapping(value = "/api/admin/{id}/img/upload")
    public void uploadImage(@RequestParam("bookPicture") MultipartFile bookPicture,
                            @PathVariable("id") String id) {
        pictureService.uploadImage(bookPicture, id);
    }

    @PostMapping("/api/user/{name}/avatars/upload")
    public void uploadAvatar(@RequestParam("avatar")MultipartFile avatar,
                             @PathVariable("name")String name,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        pictureService.uploadAvatar(avatar, name, session);
    }
}
