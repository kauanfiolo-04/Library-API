package kauanfiolo_04.library_api.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorResponseDTO(
    UUID id,
    String name,
    LocalDate birthDate,
    String nationality
) {
}
