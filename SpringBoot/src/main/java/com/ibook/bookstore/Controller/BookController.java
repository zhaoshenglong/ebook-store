package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Entity.Book;
import com.ibook.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/books/id/{id}")
    public Book getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/books/tag/all")
    public Page<Book> getBookPage(@RequestParam(name = "page", defaultValue = "0")Integer page) {
        return bookService.findBookByPage(page, 10);
    }

    @GetMapping("/books/tag/{tag}")
    public Page<Book> getBookTagPage(@PathVariable(name = "tag") String tag,
                                     @RequestParam(name = "page")Integer page) {
        System.out.println(tag);
        return bookService.findBookByTagPage(tag, page, 10);
    }

    @GetMapping("/books/search")
    public Page<Book> getBookLikePage(@RequestParam(name = "search_text")String text,
                                      @RequestParam(name = "page", defaultValue = "0")Integer page ) {
        System.out.println(text);
        return bookService.findAllLike(text,page, 10);
    }

    /**
     *
     * @param book
     * @return book
     * RequestBody : application/json
     */
    @PostMapping("admin/books/create")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    /**
     * /
     * @param data
     * @return
     * RequestBody : application/json
     */
    @PutMapping("admin/books/modify")
    public Book modifyBook(@RequestBody Map<String, String> data){
        return bookService.updateBook(data);
    }

    @DeleteMapping("/admin/books/delete")
    public void deleteBook(@RequestParam("id") String id){
        bookService.deleteBook(id);
    }




    @PostMapping("/test")
    public Book testPost(@RequestBody(required = false) String data, @RequestParam(name = "id", required = false) String id){
        Book book = new Book();
        book.setName(data);
        System.out.println(data);
        System.out.println(id);
        return book;
    }
    @PostMapping("/test1")
    public Book testPost2(@RequestBody(required = false) String data){
        Book book = new Book();
        book.setName(data);
        System.out.println(data);
        return book;
    }

    @PostMapping("/test2")
    public Book testPost(@RequestParam(name = "id", required = false) String id){
        Book book = new Book();
        book.setName(id);
        System.out.println(id);
        return book;
    }
    @PutMapping(name = "/test" )
    public Book testPut(@RequestBody Map<String, String> data){
        Book book = new Book();
        book.setName(data.get("name"));
        System.out.println(data);
        return book;
    }
    @DeleteMapping("/test")
    public Book testDelete(@RequestBody String data){
        Book book = new Book();
        book.setName(data);
        System.out.println(data);
        return book;
    }
    @GetMapping("/test")
    public Book testGet(@RequestParam(name = "data", required = false) String data){
        Book book = new Book();
        book.setName(data);
        System.out.println(data);
        return book;
    }
}
