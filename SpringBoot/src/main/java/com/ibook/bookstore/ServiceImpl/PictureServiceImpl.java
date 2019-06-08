package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Dao.BookDetailDao;
import com.ibook.bookstore.Dao.UserDetailDao;
import com.ibook.bookstore.Entity.BookDetail;
import com.ibook.bookstore.Entity.UserDetail;
import com.ibook.bookstore.Service.PictureService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private BookDetailDao bookDetailDao;

    @Override
    public void uploadAvatar(MultipartFile avatar, String name, HttpSession session) {
        try {
            UserDetail userDetail = null;
            userDetail = userDetailDao.findOne(name);
            if (userDetail == null) {
                System.out.println("Null user encountered");
                userDetail = new UserDetail();
                userDetail.setName(name);
            }
            userDetail.setAvatar(new Binary(BsonBinarySubType.BINARY, avatar.getBytes()));
            userDetailDao.saveOne(userDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] loadImage(String kind, String name)  {
        try {
            Binary image;
            if (kind.equals("user")) {
                UserDetail userDetail = userDetailDao.findOne(name);
                if (userDetail == null) {
                    System.out.println("Null value encountered");
                    return  null;
                } else {
                    image = userDetail.getAvatar();
                }
            } else {
                BookDetail bookDetail = bookDetailDao.findOne(name);
                if (bookDetail == null) {
                    System.out.println("Null value encountered");
                    return null;
                } else {
                    image = bookDetail.getPicture();
                }
            }
            return image.getData();
        } catch (Exception e){
            //...
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void uploadImage(MultipartFile image, String id) {
        try {
            BookDetail bookDetail = bookDetailDao.findOne(id);
            if (bookDetail == null)
            {
                System.out.println("Null value encountered");
                bookDetail = new BookDetail();
                bookDetail.setBookId(id);
            }
            System.out.println(image.getName() + image.getSize() + image.getContentType());
            bookDetail.setPicture(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
            bookDetailDao.saveOne(bookDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
