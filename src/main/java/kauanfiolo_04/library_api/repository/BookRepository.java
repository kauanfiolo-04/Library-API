package kauanfiolo_04.library_api.repository;

import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.model.Book;
import kauanfiolo_04.library_api.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */
public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Methods //

    List<Book> findByAuthor(Author author);

    List<Book> findByTitleLike(String title);

    List<Book> findByIsbn(String isbn);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByPublishDateBetween(LocalDate start, LocalDate end);

    List<Book> findByTitleOrIsbnOrderByTitle(String title, String Isbn);

    // JPQL com @Query //

    @Query(" select b from Book as b order by b.title, b.price ")
    List<Book> listAllOrderByTitleAndPrice();

    @Query(" select a from Book b join b.author a ")
    List<Author> listAuthorsOfBooks();

    @Query(" select distinct b.title from Book b ")
    List<String> listDifferentTitleBooks();

    @Query("""
        select b.genre
        from Book b
        join b.author a
        where a.nationality = 'Brazilian'
        order by b.genre
    """)
    List<String> listGenreBrazilianAuthors();

    // named params
    @Query(" select b from Book b where b.genre = :paramName order by :orderParam ")
    List<Book> findByGenre(
        @Param("paramName") BookGenre bookGenre,
        @Param("orderParam") String propName
    );

    //positional parameters
    @Query(" select b from Book b where b.genre = ?1 order by ?2 ")
    List<Book> findByGenrePositional(
        BookGenre bookGenre,
        String propName
    );

    @Modifying
    @Transactional
    @Query(" delete from Book where genre = ?1 ")
    void deleteByGenre(BookGenre genre);


    @Modifying
    @Transactional
    @Query(" update Book set publishDate = ?1 ")
    void updatePublishDate(LocalDate newDate);
}
