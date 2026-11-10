package kauanfiolo_04.library_api.service;

import kauanfiolo_04.library_api.exception.NotAllowedOperationException;
import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.repository.AuthorRepository;
import kauanfiolo_04.library_api.repository.BookRepository;
import kauanfiolo_04.library_api.validator.AuthorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorValidator validator;

    private final BookRepository bookRepository;

    public AuthorService(
        AuthorRepository repository,
        AuthorValidator validator,
        BookRepository bookRepository
    ) {
        this.repository = repository;
        this.validator = validator;
        this.bookRepository = bookRepository;
    }

    public Author save(Author author) {
        validator.validate(author);
        return repository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null)
            throw new IllegalArgumentException("Author not found in the DB");

        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
        if (hasAnyBook(author))
            throw new NotAllowedOperationException(
                "It isn't allowed to delete an Author that has registered books."
            );

        repository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null)
            return repository.findByNameAndNationality(name, nationality);

        if (name != null) return repository.findByName(name);

        if (nationality != null) return repository.findByNationality(nationality);

        return repository.findAll();
    }

    public boolean hasAnyBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
