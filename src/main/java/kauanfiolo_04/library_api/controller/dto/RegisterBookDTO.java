package kauanfiolo_04.library_api.controller.dto;

import kauanfiolo_04.library_api.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterBookDTO(
    String isbn,
    String title,
    LocalDateTime publishDate,
    BookGenre genre,
    BigDecimal price,
    UUID authorId
) {
}
