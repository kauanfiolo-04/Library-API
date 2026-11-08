package kauanfiolo_04.library_api.service;

import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.model.Book;
import kauanfiolo_04.library_api.model.BookGenre;
import kauanfiolo_04.library_api.repository.AuthorRepository;
import kauanfiolo_04.library_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void updatingWithoutUpdate() {
        Book book = bookRepository.
            findById(UUID.fromString("c4dfa704-efcc-4440-af6a-b9d0da3c0a04"))
            .orElse(null);

        book.setPublishDate(LocalDate.of(2024, 6 ,1));
    }

    @Transactional
    public void execute() {
        // salva autor
        Author author = new Author();
        author.setName("Batata");
        author.setBirthDate(LocalDate.of(2004, 4, 24));
        author.setNationality("Brazilian");

        authorRepository.save(author);

        // salva o livro
        Book book = new Book();
        book.setIsbn("876278342");
        book.setTitle("All kinds of potatoes");
        book.setPrice(BigDecimal.valueOf(43.89));
        book.setGenre(BookGenre.SCIENCE);
        book.setPublishDate(LocalDate.of(2012, 7, 6));
        book.setAuthor(author);

        bookRepository.save(book);

        if (author.getName().equals("Kauan")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
