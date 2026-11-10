package kauanfiolo_04.library_api.validator;

import kauanfiolo_04.library_api.exception.DuplicatedRegisterException;
import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    public void validate(Author author) {
        if (existRegisteredAuthor(author)) throw new DuplicatedRegisterException("Author already registered.");
    }

    private boolean existRegisteredAuthor(Author author) {
        Optional<Author> existedAuthor = repository.findByNameAndBirthDateAndNationality(
            author.getName(), author.getBirthDate(), author.getNationality()
        );

        if (author.getId() == null) return existedAuthor.isPresent();

        return existedAuthor.isPresent()&& !author.getId().equals(existedAuthor.get().getId());
    }
}
