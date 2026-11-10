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
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository repository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        /*
        Author author = new Author();
        author.setName("Kauan Fiolo");
        author.setBirthDate(LocalDate.of(2004, 4, 8));
        author.setNationality("Brazilian");

        authorRepository.save(author); // if you put CascadeType.ALL in the relation you won't need it */


        Book book = new Book();
        book.setIsbn("080408040804");
        book.setTitle("Kauan Fiolo");
        book.setPrice(BigDecimal.valueOf(84.25));
        book.setGenre(BookGenre.BIOGRAPHY);
        book.setPublishDate(LocalDate.of(2004, 4, 8));

        Author author = authorRepository
            .findById(UUID.fromString("6ea9d558-79c9-4d21-a669-b7fce293f333"))
            .orElse(null);

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void updateAuthorOfTheBook() {
        UUID bookId = UUID.fromString("f6752a02-777f-4142-9e3e-e4c186d9b87e");
        Book bookToUpdate = repository.findById(bookId).orElse(null);

        UUID authorId = UUID.fromString("cdd3f8ff-eb60-4cfb-a875-97f5b2000eea");
        Author author = authorRepository.findById(authorId).orElse(null);

        bookToUpdate.setAuthor(author);

        repository.save(bookToUpdate);
    }

    @Test
    void delete() {
        UUID bookId = UUID.fromString("f6752a02-777f-4142-9e3e-e4c186d9b87e");
        repository.deleteById(bookId);
    }

    @Test
    @Transactional //precisa usar quando se usa o FetchType.LAZY na relação
    void findBookTest() {
        UUID id = UUID.fromString("c4dfa704-efcc-4440-af6a-b9d0da3c0a04");

        Book book = repository.findById(id).orElse(null);

        System.out.println("Book: ");
        System.out.println(book.getTitle());

        System.out.println("Author: ");
        System.out.println(book.getAuthor().getName());
    }

    @Test
    void searchByTitleTest() {
        List<Book> list = repository.findByTitleLike("Star Wars%");
        list.forEach(System.out::println);
    }

    @Test
    void searchByIsbnTest() {
        List<Book> list = repository.findByIsbn("24323423423");
        list.forEach(System.out::println);
    }

    @Test
    void searchByTitleAndPrice() {
        List<Book> list = repository.findByTitleAndPrice("LOTR", BigDecimal.valueOf(243.50));
        list.forEach(System.out::println);
    }

    @Test
    void listBooksWithQueryJPQL() {
        var result = repository.listAllOrderByTitleAndPrice();

        result.forEach(System.out::println);
    }

    @Test
    void listAuthorsOfBooks() {
        var result = repository.listAuthorsOfBooks();

        result.forEach(System.out::println);
    }

    @Test
    void listDistinctTitleOfBooks() {
        var result = repository.listDifferentTitleBooks();

        result.forEach(System.out::println);
    }

    @Test
    void listGenreBrazilianAuthors() {
        var result = repository.listGenreBrazilianAuthors();

        result.forEach(System.out::println);
    }

    @Test
    void listByGenreQueryParamTest() {
        var result = repository.findByGenre(BookGenre.FANTASY, "publishDate");

        result.forEach(System.out::println);
    }

    @Test
    void listByGenreQueryPositionalParamTest() {
        var result = repository.findByGenrePositional(BookGenre.FANTASY, "publishDate");

        result.forEach(System.out::println);
    }

    @Test
    void deleteByGenreTest() {
        repository.deleteByGenre(BookGenre.BIOGRAPHY);
    }

    @Test
    void updatePublishDateTest() {
        repository.updatePublishDate(LocalDate.of(2000, 2, 4));
    }
}