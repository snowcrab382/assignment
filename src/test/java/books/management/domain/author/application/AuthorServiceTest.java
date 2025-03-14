package books.management.domain.author.application;

import static books.management.fixtures.AuthorFixture.createAuthor;
import static books.management.fixtures.AuthorFixture.createAuthorRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import books.management.domain.author.dao.AuthorRepository;
import books.management.domain.author.domain.Author;
import books.management.domain.author.dto.request.AuthorRequestDto;
import books.management.global.error.exception.EntityNotFoundException;
import books.management.global.error.exception.NonUniqueValueException;
import books.management.global.error.response.GlobalErrorCode;
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
@DisplayName("[단위 테스트] AuthorService")
class AuthorServiceTest {

    @InjectMocks
    AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    @Nested
    @DisplayName("저자 생성 테스트")
    class Create {

        @Test
        @DisplayName("성공")
        void create_success() {
            // given
            AuthorRequestDto request = createAuthorRequestDto("저자", "test@email.com");
            given(authorRepository.existsByEmail(any())).willReturn(false);

            // when
            authorService.create(request);

            // then
            then(authorRepository).should().save(any());
        }

        @Test
        @DisplayName("실패 - 중복된 이메일이 존재하면 예외 발생")
        void create_throwException_ifEmailExists() {
            // given
            AuthorRequestDto request = createAuthorRequestDto("저자", "test@email.com");
            given(authorRepository.existsByEmail(any())).willReturn(true);

            // when & then
            assertThatThrownBy(() -> authorService.create(request))
                    .isInstanceOf(NonUniqueValueException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.AUTHOR_EMAIL_DUPLICATION);
        }

    }

    @Nested
    @DisplayName("저자 목록 조회 테스트")
    class FindAll {

        @Test
        @DisplayName("성공 - 저자 목록이 비어있어도 빈 리스트 반환")
        void findAll_success() {
            // given
            Author author1 = createAuthor("저자1", "test1@email.com");
            Author author2 = createAuthor("저자2", "test2@email.com");
            given(authorRepository.findAll()).willReturn(List.of(author1, author2));

            // when
            authorService.findAllAuthor();

            // then
            then(authorRepository).should().findAll();
        }

        @Test
        @DisplayName("성공 - 저자 목록이 비어있어도 빈 리스트 반환")
        void findAll_success_returnEmptyList() {
            // given
            given(authorRepository.findAll()).willReturn(List.of());

            // when
            authorService.findAllAuthor();

            // then
            then(authorRepository).should().findAll();
        }
    }

    @Nested
    @DisplayName("저자 상세 조회 테스트")
    class FindAuthorById {

        @Test
        @DisplayName("성공")
        void findAuthorById_success() {
            // given
            Long authorId = 1L;
            Author author = createAuthor("저자", "test@email.com");
            given(authorRepository.findById(authorId)).willReturn(Optional.ofNullable(author));

            // when
            authorService.findAuthorById(authorId);

            // then
            then(authorRepository).should().findById(authorId);
        }

        @Test
        @DisplayName("실패 - 저자가 존재하지 않으면 예외 발생")
        void findAuthorById_throwException_ifAuthorNotExists() {
            // given
            Long authorId = 1L;
            given(authorRepository.findById(authorId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> authorService.findAuthorById(authorId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.AUTHOR_NOT_FOUND);
        }

    }

    @Nested
    @DisplayName("저자 수정 테스트")
    class Update {

        @Test
        @DisplayName("성공")
        void update_success() {
            // given
            Long authorId = 1L;
            AuthorRequestDto request = createAuthorRequestDto("수정된 저자", "test@email.com");
            Author author = createAuthor("저자", "test@email.com");
            given(authorRepository.findById(authorId)).willReturn(Optional.ofNullable(author));

            // when
            authorService.updateAuthorDetails(authorId, request);

            // then
            assertThat(author.getName()).isEqualTo("수정된 저자");
        }

        @Test
        @DisplayName("실패 - 저자가 존재하지 않으면 예외 발생")
        void updateAuthor_throwException_ifAuthorNotExists() {
            // given
            Long authorId = 1L;
            AuthorRequestDto request = createAuthorRequestDto("저자", "test@email.com");
            given(authorRepository.findById(authorId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> authorService.updateAuthorDetails(authorId, request))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.AUTHOR_NOT_FOUND);
        }

        @Test
        @DisplayName("실패 - 저자 이메일 수정 시 중복된 이메일이 이미 존재하면 예외 발생")
        void updateAuthor_throwException_ifEmailExists() {
            // given
            Long authorId = 1L;
            Author author = createAuthor("저자", "test@email.com");
            AuthorRequestDto request = createAuthorRequestDto("저자", "test2@email.com");
            given(authorRepository.findById(authorId)).willReturn(Optional.ofNullable(author));
            given(authorRepository.existsByEmail(any())).willReturn(true);

            // when & then
            assertThatThrownBy(() -> authorService.updateAuthorDetails(authorId, request))
                    .isInstanceOf(NonUniqueValueException.class)
                    .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.AUTHOR_EMAIL_DUPLICATION);

        }
    }

    @Nested
    @DisplayName("저자 삭제 테스트")
    class Delete {

        @Test
        @DisplayName("성공")
        void delete_success() {
            // given
            Long authorId = 1L;

            // when
            authorService.deleteAuthor(authorId);

            // then
            then(authorRepository).should().deleteById(authorId);
        }

        @Test
        @DisplayName("성공 - 저자가 존재하지 않아도 삭제 성공")
        void delete_success_ifAuthorNotExists() {
            // given
            Long authorId = 1L;

            // when
            authorService.deleteAuthor(authorId);

            // then
            then(authorRepository).should().deleteById(authorId);
        }

    }
}