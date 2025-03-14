package books.management.domain.book.application;

import static books.management.fixtures.AuthorFixture.createAuthor;
import static books.management.fixtures.BookFixture.createBook;
import static books.management.fixtures.BookFixture.createBookRequestDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import books.management.domain.author.dao.AuthorRepository;
import books.management.domain.author.domain.Author;
import books.management.domain.book.dao.BookRepository;
import books.management.domain.book.domain.Book;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import books.management.global.error.exception.EntityNotFoundException;
import books.management.global.error.exception.NonUniqueValueException;
import books.management.global.error.response.GlobalErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("[단위 테스트] BookService")
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Nested
    @DisplayName("도서 생성 테스트")
    class Create {

        @Test
        @DisplayName("성공")
        void create_Success() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("제목", "설명", "1234567890", LocalDate.now(), 1L);
            Author author = createAuthor("저자", "test@email.com");

            given(bookRepository.existsByIsbn(any())).willReturn(false);
            given(authorRepository.findById(any())).willReturn(Optional.ofNullable(author));

            // when
            bookService.create(bookRequestDto);

            // then
            then(bookRepository).should().save(any());

        }

        @Test
        @DisplayName("실패 - ISBN이 이미 존재한다면 예외 발생")
        void create_ThrowException_IfIsbnExists() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("제목", "설명", "1234567890", LocalDate.now(), 1L);

            given(bookRepository.existsByIsbn(any())).willReturn(true);

            // when & then
            assertThatThrownBy(() -> bookService.create(bookRequestDto))
                    .isInstanceOf(NonUniqueValueException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.BOOK_ISBN_DUPLICATION);
        }

    }

    @Nested
    @DisplayName("도서 목록 전체 조회 테스트")
    class FindAllBooks {

        @Test
        @DisplayName("성공 - 도서 목록이 비어있어도 빈 리스트 반환")
        void findAllBooks_success_returnEmptyList() {
            // given
            given(bookRepository.findAll()).willReturn(List.of());

            // when
            List<BookResponseDto> books = bookService.findAllBooks();

            // then
            assertThat(books.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("성공 - 도서 목록이 존재하면 목록 반환")
        void findAllBooks_success_returnBooks() {
            // given
            Author author = createAuthor("저자1", "test@email.com");
            Book book1 = createBook("제목1", "설명1", "1234567890", LocalDate.now(), author);
            Book book2 = createBook("제목2", "설명2", "1234567891", LocalDate.now(), author);
            given(bookRepository.findAll()).willReturn(List.of(book1, book2));

            // when
            List<BookResponseDto> books = bookService.findAllBooks();

            // then
            assertThat(books.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("도서 상세 조회 테스트")
    class FindBookById {

        @Test
        @DisplayName("성공 - 도서가 존재하면 상세 정보 반환")
        void findBookById_success_returnBookDetails() {
            // given
            Author author = createAuthor("저자", "test@email.com");
            Book book = createBook("제목", "설명", "1234567890", LocalDate.now(), author);
            given(bookRepository.findById(any())).willReturn(Optional.of(book));

            // when
            BookResponseDto bookResponseDto = bookService.findBookById(1L);

            // then
            assertThat(bookResponseDto).isNotNull();
        }

        @Test
        @DisplayName("실패 - 도서가 존재하지 않으면 예외 발생")
        void findBookById_ThrowException_IfBookNotExists() {
            // given
            given(bookRepository.findById(any())).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> bookService.findBookById(1L))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.BOOK_NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("도서 정보 수정 테스트")
    class UpdateBookDetails {

        @Test
        @DisplayName("성공 - 동일한 도서의 ISBN을 수정하면 고유성 검사를 수행하지 않음")
        void updateBookDetails_success() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("수정된 제목", "설명", "1234567890", LocalDate.now(), 1L);
            Author author = createAuthor("저자", "test@email.com");
            Book book = createBook("제목", "설명", "1234567890", LocalDate.now(), author);
            given(bookRepository.findById(any())).willReturn(Optional.of(book));
            given(authorRepository.findById(any())).willReturn(Optional.of(author));

            // when
            bookService.updateBookDetails(1L, bookRequestDto);

            // then
            assertThat(book.getTitle()).isEqualTo("수정된 제목");
        }

        @Test
        @DisplayName("실패 - 도서가 존재하지 않으면 예외 발생")
        void updateBookDetails_ThrowException_IfBookNotExists() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("수정된 제목", "설명", "1234567890", LocalDate.now(), 1L);
            given(bookRepository.findById(any())).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> bookService.updateBookDetails(1L, bookRequestDto))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.BOOK_NOT_FOUND);
        }

        @Test
        @DisplayName("실패 - ISBN 값 수정 시 이미 존재한다면 예외 발생")
        void updateBookDetails_ThrowException_IfIsbnExists() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("수정된 제목", "설명", "2234567890", LocalDate.now(), 1L);
            Author author = createAuthor("저자", "test@email.com");
            Book book = createBook("제목", "설명", "1234567890", LocalDate.now(), author);
            given(bookRepository.findById(any())).willReturn(Optional.of(book));
            given(bookRepository.existsByIsbn(any())).willReturn(true);

            // when & then
            assertThatThrownBy(() -> bookService.updateBookDetails(1L, bookRequestDto))
                    .isInstanceOf(NonUniqueValueException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.BOOK_ISBN_DUPLICATION);
        }

        @Test
        @DisplayName("실패 - 저자가 존재하지 않으면 예외 발생")
        void updateBookDetails_ThrowException_IfAuthorNotExists() {
            // given
            BookRequestDto bookRequestDto = createBookRequestDto("수정된 제목", "설명", "1234567890", LocalDate.now(), 1L);
            Book book = createBook("제목", "설명", "1234567890", LocalDate.now(), null);
            given(bookRepository.findById(any())).willReturn(Optional.of(book));
            given(authorRepository.findById(any())).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> bookService.updateBookDetails(1L, bookRequestDto))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.AUTHOR_NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("도서 삭제 테스트")
    class Delete {

        @Test
        @DisplayName("성공")
        void delete_Success() {
            // given

            // when
            bookService.delete(1L);

            // then
            then(bookRepository).should().deleteById(1L);
        }

        @Test
        @DisplayName("성공 - 도서가 존재하지 않아도 삭제 요청이 들어오면 성공")
        void delete_Success_IfBookNotExists() {
            // given

            // when
            bookService.delete(1L);

            // then
            then(bookRepository).should().deleteById(1L);
        }
    }

}