# Cloud computing and Neo4j 

## Part 1 

For part 1, only a file is required. Related file is under the root directory. Full path is
`/assignment8-part1/README.pdf`.

## Part 2

For part 2, I implemented methods in several layers. Related files are listed below.

* Entity - `com.ibook.bookstore.Entity`
    * Person
* Repository - `com.ibook.bookstore.Repository`
    * PersonRepository
* Dao - `com.ibook.bookstore.Dao`
    * PersonDao
* DaoImpl - `com.ibook.bookstore.DaoImpl`
    * PersonDaoImpl
* Service - `com.ibook.bookstore.Service`
    * PersonService
* ServiceImpl - `com.ibook.bookstore.ServiceImpl`
    * PersonServiceImpl
* Controller - `com.ibook.bookstore.Controller`
    * PersonController
    
    
Currently, friend relationship only supports `follow` and `unFollow` operations.
`follow` operation would result in a new `following` in the origin user and a new `follower` in the target user.
`unFollow` would result in the corresponding item being deleted.

Frontend Interface is not implemented now, the test is done under the support of `Postman`.
