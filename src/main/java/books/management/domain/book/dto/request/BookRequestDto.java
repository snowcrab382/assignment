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

    @NotNull(message = "제목은 필수 입력 값입니다.")
    private String title;
    private String description;

    @NotNull(message = "ISBN은 필수 입력 값입니다.")
    @Length(min = 10, max = 10)
    @Pattern(regexp = "^(?:10|[1-8][0-9]|90)[0-9]{7}0$", message = "ISBN 형식에 맞지 않습니다.")
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @NotNull(message = "저자 ID는 필수 입력 값입니다.")
    private Long authorId;

    private BookRequestDto(String title, String description, String isbn, LocalDate publicationDate, Long authorId) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
    }

}
