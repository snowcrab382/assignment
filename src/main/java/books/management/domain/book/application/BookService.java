package books.management.domain.book.application;

import books.management.domain.author.application.AuthorService;
import books.management.domain.author.domain.Author;
import books.management.domain.book.dao.BookRepository;
import books.management.domain.book.domain.Book;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public void create(BookRequestDto request) {
        Author author = authorService.findAuthorById(request.getAuthorId());
        Book book = Book.from(request, author);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .map(BookResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDto findBookById(Long id) {
        Book book = findById(id);
        return BookResponseDto.from(book);
    }

    public void updateBookDetails(Long id, BookRequestDto request) {
        Book book = findById(id);
        Author author = authorService.findAuthorById(request.getAuthorId());
        book.update(request.getTitle(), request.getDescription(), request.getIsbn(), request.getPublicationDate(),
                author);
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
