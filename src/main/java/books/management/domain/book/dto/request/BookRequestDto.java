package books.management.domain.book.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRequestDto {

    @NotNull
    private String title;
    private String description;

    @NotNull
    @Length(min = 10, max = 10)
    @Pattern(regexp = "^(?:10|[1-8][0-9]|90)[0-9]{7}0$")
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @NotNull
    private Long authorId;

    private BookRequestDto(String title, String description, String isbn, LocalDate publicationDate, Long authorId) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
    }

}
