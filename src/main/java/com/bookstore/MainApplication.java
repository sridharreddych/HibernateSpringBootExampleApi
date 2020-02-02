package com.bookstore;

import com.bookstore.entity.Book;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    
    private final BookstoreService bookstoreService;
    
    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
    
    @Bean
    public ApplicationRunner init() {
        return args -> {
            
            System.out.println("Find a book:");

            // this can come via a controller endpoint
            Book book = new Book();
            book.setTitle("Carrie");
            book.setGenre("Horror");
            book.setIsbn("001-OG");
            book.setAuthor("Olivia Goy");
            book.setPrice(23);
            
            boolean foundAnd = bookstoreService.existsBook1(book);            
            System.out.println("Found (existsBook1): " + foundAnd + "\n");
            
            boolean foundOr = bookstoreService.existsBook2(book);            
            System.out.println("Found (existsBook2): " + foundOr + "\n");
            
            boolean foundIgnorePath = bookstoreService.existsBook3(book);            
            System.out.println("Found (existsBook3): " + foundIgnorePath + "\n");
        };
    }
}
/*
*How To Check If A Transient Entity Exists In The Database Via Spring Query By Example (QBE)

Description: This application is an example of using Spring Data Query By Example (QBE) to check if a transient entity exists in the database. Consider the Book entity and a Spring controller that exposes an endpoint as: public String checkBook(@Validated @ModelAttribute Book book, ...). Beside writting an explicit JPQL, we can rely on Spring Data Query Builder mechanism or, even better, on Query By Example (QBE) API. In this context, QBE API is quite useful if the entity has a significant number of attributes and:

for all attributes, we need a head-to-head comparison of each attribute value to the corresponding column value
for a subset of attributes, we need a head-to-head comparison of each attribute value to the corresponding column value
for a subset of attributes, we return true at first match between an attribute value and the corresponding column value
any other scenario
Key points:

the repository, BookRepository extends QueryByExampleExecutor
the application uses <S extends T> boolean exists(Example<S> exmpl) with the proper probe (an entity instance populated with the desired fields values)
moreover, the probe relies on ExampleMatcher which defines the details on how to match particular fields
Note: Do not conclude that Query By Example (QBE) defines only the exists() method. Check out all methods here.
*/