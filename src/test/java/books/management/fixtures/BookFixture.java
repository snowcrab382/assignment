package books.management.fixtures;

import books.management.domain.author.domain.Author;
import books.management.domain.book.domain.Book;
import books.management.domain.book.dto.request.BookRequestDto;
import java.time.LocalDate;

public class BookFixture {

    public static BookRequestDto createBookRequestDto(String title, String description, String isbn,
                                                      LocalDate publicationDate, Long authorId) {
        return BookRequestDto
                .builder()
                .title(title)
                .description(description)
                .isbn(isbn)
                .publicationDate(publicationDate)
                .authorId(authorId)
                .build();
    }

    public static Book createBook(String title, String description, String isbn, LocalDate publicationDate,
                                  Author author) {
        return Book
                .builder()
                .title(title)
                .description(description)
                .isbn(isbn)
                .publicationDate(publicationDate)
                .author(author)
                .build();
    }
}
