# Assignment 4 Transaction and Concurrency Control

## Run the Application

* Port Reserved
    - 8081 (Frontend) 
    - 8080 (Backend)
    - kafka default reserved ports
    - zookeeper default reserved ports
    - 9092 Rmi

### Run frontend
> npm install
> npm start
>
### Run backend
> mvn install
> cd target/classes; rmiregistry

Start zookeeper & kafka, using quick start
> cd <kafka root directory>
> bin/zookeeper-server-start.sh config/zookeeper.properties
> bin/kafka-server-start.sh config/server.properties

After that 
> mvn springboot:run 

The backend application should now be running
  
## Requirements

- 在系统中增加事务控制功能，至少使用 3 种不同的事务属性来观察它们之间的差异；
- 仿照课件中给出的例子，在你的E-Book的首页上增加一个访问量统计功能，通过多线程控制，确保计数值准确，不会出现因多用户同时访问而统计不准确的情况。

## Implementation

### Part 1

- Add `Required`
`Required` annotation requires a transaction during execution. It is also the default state of 
Spring boot methods.

- Add `NotSupported`
`NotSupported` suspends current transaction if necessary. 

- Add `Supports`
`Supports` join current transaction if provided.

I add `NotSupported` to `Query` methods. Because query may not update data and query rarely throws
exceptions.

`Supports` is used to annotated `save` & `delete` methods in DAO Layer.

`Required` is used to annotated `save` & `delete` methods in Service Layer.

Take delete book as example. In Service Layer, the code is shown below.
```java
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(String id) {
        Book book = bookDao.findOne(id);
        bookDao.deleteBook(book);
        // throw new RuntimeException("Delete book exception");
    }
```
if we uncommented the remark, exception would be thrown, and if we make no annotation to `bookDao.deleteBook`, the book would not
be deleted. That because `REQUIRED` is the default behavior in SpringBoot Transaction

If we make `NOT_SUPPORTED` annotation to `bookDao.deleteBook`, the book would be deleted.

If service layer rollback, dao layer should also rollback. So `NotSupported` annotation is not suitable for `delete/update` methods
in DAO layer. The reason for choosing `SUPPORTS` is that the decision should be made by service. If Service layer need transaction, then 
DAO layer join the transaction, do nothing otherwise.



### Part 2

#### Frontend
- Add `views` component in homepage. Every time, a user access homepage would trigger an invrement.

#### Backend
- Add `synchronize` key word function. Use `synchronize` to ensure atomicity and accuracy.