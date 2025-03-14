package books.management.domain.book.application;

import books.management.domain.author.dao.AuthorRepository;
import books.management.domain.author.domain.Author;
import books.management.domain.book.dao.BookRepository;
import books.management.domain.book.domain.Book;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import books.management.global.error.exception.EntityNotFoundException;
import books.management.global.error.exception.NonUniqueValueException;
import books.management.global.error.response.GlobalErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * 새로운 도서를 생성합니다. - ISBN의 고유성을 유지하기 위해 가장 먼저 데이터베이스에 동일한 값이 존재하는지 확인하는 로직이 실행됩니다.
     *
     * @param request
     */
    public void create(BookRequestDto request) {
        validateIsbn(request.getIsbn());
        Author author = findAuthorById(request.getAuthorId());
        Book book = Book.from(request, author);
        bookRepository.save(book);
    }

    /**
     * 저장된 모든 도서 목록을 조회합니다. - findAll() 메서드를 사용하기 때문에 해당 테이블의 모든 데이터를 조회하는 것이므로, 데이터가 많을 경우 성능 문제가 발생할 수 있습니다.
     *
     * @return
     */
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

    /**
     * 도서 상세 정보를 수정합니다. - 도서의 ISBN이 변경되었을 경우, ISBN의 고유성 검증 로직을 실행합니다. - 도서와 연관 관계를 맺는 저자 정보가 존재하지 않을 경우 예외가 발생합니다.
     *
     * @param id
     * @param request
     */
    public void updateBookDetails(Long id, BookRequestDto request) {
        Book book = findById(id);

        if (!book.getIsbn().equals(request.getIsbn())) {
            validateIsbn(request.getIsbn());
        }

        Author author = findAuthorById(request.getAuthorId());
        book.update(request.getTitle(), request.getDescription(), request.getIsbn(), request.getPublicationDate(),
                author);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * ISBN의 고유성을 검증하기 위해 데이터베이스에 동일한 ISBN이 존재하는지 확인합니다.
     *
     * @param isbn
     */
    private void validateIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new NonUniqueValueException(GlobalErrorCode.BOOK_ISBN_DUPLICATION);
        }
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.BOOK_NOT_FOUND));
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.AUTHOR_NOT_FOUND));
    }
}
