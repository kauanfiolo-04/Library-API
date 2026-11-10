package kauanfiolo_04.library_api.controller;

import kauanfiolo_04.library_api.controller.dto.AuthorDTO;
import kauanfiolo_04.library_api.controller.dto.AuthorResponseDTO;
import kauanfiolo_04.library_api.controller.dto.ResponseError;
import kauanfiolo_04.library_api.exception.DuplicatedRegisterException;
import kauanfiolo_04.library_api.exception.NotAllowedOperationException;
import kauanfiolo_04.library_api.model.Author;
import kauanfiolo_04.library_api.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AuthorDTO authorDTO) {
        try {
            Author author = authorDTO.mapToAuthor();
            service.save(author);

            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicatedRegisterException e) {
            ResponseError errorDTO = ResponseError.conflict(e.getMessage());

            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
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
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            UUID authorId = UUID.fromString(id);

            Optional<Author> authorOpt = service.getById(authorId);

            if (authorOpt.isEmpty()) return ResponseEntity.notFound().build();

            service.delete(authorOpt.get());
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperationException e) {
            ResponseError errorDTO = ResponseError.defaultResponse(e.getMessage());

            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> search(
        @RequestParam(value = "name", required = false)
        String name,
        @RequestParam(value = "nationality", required = false)
        String nationality
    ) {
        List<Author> authorList = service.search(name, nationality);

        List<AuthorResponseDTO> response =
            authorList.stream().map(
                a -> new AuthorResponseDTO(a.getId(), a.getName(), a.getBirthDate(), a.getNationality())
            ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable("id") String id,
        @RequestBody AuthorDTO dto
    ) {
        try {
            UUID authorId = UUID.fromString(id);

            Optional<Author> authorOpt = service.getById(authorId);

            if (authorOpt.isEmpty()) return ResponseEntity.notFound().build();

            Author author = authorOpt.get();
            author.setName(dto.name());
            author.setBirthDate(dto.birthDate());
            author.setNationality(dto.nationality());

            service.update(author);

            return ResponseEntity.noContent().build();
        } catch (DuplicatedRegisterException e) {
            ResponseError errorDTO = ResponseError.conflict(e.getMessage());

            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
