package books.management.domain.book.dto.response;

import books.management.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookResponseDto {

    private Long id;
    private String title;
    private String description;
    private Long isbn;
    private String publicationDate;
    private Long authorId;

    @Builder
    public BookResponseDto(Long id, String title, String description, Long isbn, String publicationDate,
                           Long authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
    }

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate().toString())
                .authorId(book.getAuthor().getId())
                .build();
    }
}
