package kauanfiolo_04.library_api.controller.dto;

import java.time.LocalDate;

public record AuthorDTO(
    String name,
    LocalDate birthDate,
    String nationality
) {
}
