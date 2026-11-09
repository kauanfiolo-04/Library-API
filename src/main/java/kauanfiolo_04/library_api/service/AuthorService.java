package kauanfiolo_04.library_api.service;

import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }
}
