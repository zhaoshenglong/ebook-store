package com.ibook.bookstore.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PictureService {
    void uploadAvatar(MultipartFile avatar, String name, HttpSession session);
    byte[] loadImage(String kind, String name);
    void uploadImage(MultipartFile image, String id);
}
