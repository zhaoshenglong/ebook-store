package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Log.LogService;
import com.ibook.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/api/public/books/id/{id}")
    public Book getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/api/public/books/tag/all")
    public Page<Book> getBookPage(@RequestParam(name = "page", defaultValue = "0")Integer page) {
        return bookService.findBookByPage(page, 10, "user");
    }

    @GetMapping("/api/public/books/tag/{tag}")
    public Page<Book> getBookTagPage(@PathVariable(name = "tag") String tag,
                                     @RequestParam(name = "page", defaultValue = "0")Integer page) {
        System.out.println(tag);
        return bookService.findBookByTagPage(tag, page, 10);
    }

    @GetMapping("/api/public/books/search")
    public Page<Book> getBookLikePage(@RequestParam(name = "search_text")String text,
                                      @RequestParam(name = "page", defaultValue = "0")Integer page,
                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("name");
        if (user == null) {
            return null;
        } else {
            return bookService.findAllLike(text,page, 10, user);
        }
    }

    @GetMapping("/api/admin/books/all")
    public Page<Book> getAllBookByPage(@RequestParam(name = "page", defaultValue = "0")Integer page) {
        return bookService.findBookByPage(page, 10, "admin");
    }

    @GetMapping("/api/admin/books/statistics/all")
    public List<Book> getAllBooks() {
        return bookService.findAllBook();
    }

    @GetMapping("/api/admin/books/search")
    public Page<Book> getAllBookLikeByPage(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                           @RequestParam(name = "pattern", defaultValue = "")String pattern) {
        return bookService.findAllLike(pattern, page, 10, "admin");
    }

    @PostMapping("/api/admin/books/create")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }


    @PutMapping("/api/admin/books/modify")
    public Book modifyBook(@RequestBody Map<String, String> data){
        return bookService.updateBook(data);
    }

    @DeleteMapping("/api/admin/books/delete")
    public void deleteBook(@RequestParam("id") String id){
        bookService.deleteBook(id);
    }


}
