package kauanfiolo_04.library_api.service;

import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null)
            throw new IllegalArgumentException("Author not found in the DB");

        repository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
        repository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null)
            return repository.findByNameAndNationality(name, nationality);

        if (name != null) return repository.findByName(name);

        if (nationality != null) return repository.findByNationality(nationality);

        return repository.findAll();
    }
}
