package books.management.domain.book.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRequestDto {

    private String title;
    private String description;
    private Long isbn;
    private LocalDate publicationDate;
    private Long authorId;

    private BookRequestDto(String title, String description, Long isbn, LocalDate publicationDate, Long authorId) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
    }

}
