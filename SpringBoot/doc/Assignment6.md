# Assignment 6 Web service and Map Reduce

## Web Service
For this part, I implemented my service with RESTful interface. The interface for web service are in
`com.ibook.bookstore.Controller` package.

In `com.ibook.bookstore.Controller` package, there are some `Controller`s, and all of these controller are RESTController.
What they do is only accept requests and forward to service layer. After returning from service, format the 
result into json and send back to client.

The code is like below
```java
@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/api/public/books/id/{id}")
    public Book getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }
}
```

## Map Reduce
Implemented a simple log parsing according to the example provided by teacher.
Related files are in the `log` package.

In the `log` package, there are three Classes, `Mapper`, `Reducer` and main program entrance.

The logic is to parse `app.log` file and count different levels of logging like `DEBUG`, `WARN`, `FATAL`, `ERROR`, `INFO`.
Like `WordCount`, it finally output count for each levels into the given directory.
