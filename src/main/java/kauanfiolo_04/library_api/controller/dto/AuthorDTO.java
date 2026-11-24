package kauanfiolo_04.library_api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import kauanfiolo_04.library_api.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
    @NotBlank(message = "required field")
    @Size(min = 2, max = 100, message = "out of pattern field.")
    String name,
    @NotNull(message = "required field")
    @Past(message = "it must be a past date.")
    LocalDate birthDate,
    @NotBlank(message = "required field")
    @Size(min = 2, max = 50, message = "out of pattern field.")
    String nationality
) {

    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);

        return author;
    }
}
