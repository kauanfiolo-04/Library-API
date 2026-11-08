package kauanfiolo_04.library_api.repository;

import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.model.Book;
import kauanfiolo_04.library_api.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("Kauan");
        author.setNationality("Brazilian");
        author.setBirthDate(LocalDate.of(2004, 4, 8));

        var savedAuthor = repository.save(author);

        System.out.println(savedAuthor);
    }

    @Test
    public void updateTest() {
        UUID id = UUID.fromString("4bbf4073-6db4-4756-9476-23e274fa9f11");

        Optional<Author> author = repository.findById(id);

        if (author.isPresent()) {
            Author a = author.get();

            System.out.println("Author data: ");
            System.out.println(a);

            a.setBirthDate(LocalDate.of(1950, 1, 30));

            repository.save(a);
        }
    }

    @Test
    public void listTest() {
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Author count: " + repository.count());
    }

    @Test
    public void deleteTest() {
        UUID id = UUID.fromString("4bbf4073-6db4-4756-9476-23e274fa9f11");

        repository.deleteById(id);
    }

    @Test
    public void saveAuthorWithBooksTest() {
        Author author = new Author();
        author.setName("Beatriz");
        author.setBirthDate(LocalDate.of(2004, 6, 1));
        author.setNationality("Spanish");

        Book book = new Book();
        book.setIsbn("546758678");
        book.setTitle("Star Wars");
        book.setPrice(BigDecimal.valueOf(189.99));
        book.setGenre(BookGenre.FICTION);
        book.setPublishDate(LocalDate.of(1964, 5, 2));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("985697856756");
        book2.setTitle("Star Wars II");
        book2.setPrice(BigDecimal.valueOf(209.99));
        book2.setGenre(BookGenre.FICTION);
        book2.setPublishDate(LocalDate.of(1970, 3, 23));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);

        // bookRepository.saveAll(author.getBooks()); -- usei CascadeType.ALL na relação
    }

    @Test
    public void listBooksOfAuthor() {
        UUID id = UUID.fromString("f2f00418-b3f0-42f5-a5d6-2b8b909224f1");
        Author author = repository.findById(id).get();

        List<Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);

        author.getBooks().forEach(System.out::println);
    }
}
