
this very simple cucumber-jvm project demonstrates the use of Cucumber to execute acceptance tests in Java
using Spring for dependency injection and for managing JPA persistence using Spring ORM, Spring Data JPA and
Eclipselink and writes persistent data to an HSQL in memory DB

There are 2 entity classes Book and Author. There is a bidirectional many to many relationship between them
 as a Book will have one or more Authors, and an Author may contribute to zero or more Books

The system has 2 features a first concerning Authors and the other concerning Books

Found the beginnings of this example in the book Cucumber Recipes
