package kauanfiolo_04.library_api.repository;

import kauanfiolo_04.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
