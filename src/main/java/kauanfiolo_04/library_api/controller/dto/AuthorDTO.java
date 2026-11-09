package kauanfiolo_04.library_api.controller.dto;

import kauanfiolo_04.library_api.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
    String name,
    LocalDate birthDate,
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
