package kauanfiolo_04.library_api.controller;

import kauanfiolo_04.library_api.controller.dto.AuthorDTO;
import kauanfiolo_04.library_api.controller.dto.AuthorResponseDTO;
import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.service.AuthorService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorDTO authorDTO) {
        Author author = authorDTO.mapToAuthor();
        service.save(author);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(author.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable("id") String id) {
        UUID authorId = UUID.fromString(id);

        Optional<Author> authorOpt = service.getById(authorId);

        if (authorOpt.isPresent()) {
            Author author = authorOpt.get();

            AuthorResponseDTO response =
                new AuthorResponseDTO(
                    author.getId(), author.getName(), author.getBirthDate(), author.getNationality()
                );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        UUID authorId = UUID.fromString(id);

        Optional<Author> authorOpt = service.getById(authorId);

        if (authorOpt.isEmpty()) return ResponseEntity.notFound().build();

        service.delete(authorOpt.get());
        return ResponseEntity.noContent().build();
    }
}
