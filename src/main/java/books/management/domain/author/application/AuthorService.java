package books.management.domain.author.application;

import books.management.domain.author.dao.AuthorRepository;
import books.management.domain.author.domain.Author;
import books.management.domain.author.dto.request.AuthorRequestDto;
import books.management.domain.author.dto.response.AuthorResponseDto;
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
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * 새로운 저자를 생성합니다. - 이메일의 고유성을 유지하기 위해 가장 먼저 데이터베이스에 동일한 값이 존재하는지 확인하는 로직이 실행됩니다.
     *
     * @param request
     */
    public void create(AuthorRequestDto request) {
        validateEmail(request.getEmail());
        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

    /**
     * 저장된 모든 저자 목록을 조회합니다. - findAll() 메서드를 사용하기 때문에 해당 테이블의 모든 데이터를 조회하는 것이므로, 데이터가 많을 경우 성능 문제가 발생할 수 있습니다.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> findAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(AuthorResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto findAuthorById(Long id) {
        Author author = findById(id);
        return AuthorResponseDto.from(author);
    }

    /**
     * 저자 상세 정보를 수정합니다. - 저자의 이메일이 변경되었을 경우, 이메일의 고유성 검증 로직을 실행합니다.
     *
     * @param id
     * @param request
     */
    public void updateAuthorDetails(Long id, AuthorRequestDto request) {
        Author author = findById(id);

        if (!author.getEmail().equals(request.getEmail())) {
            validateEmail(request.getEmail());
        }

        author.update(request.getName(), request.getEmail());
    }

    /**
     * 저자를 삭제합니다. - 저자와 도서 간의 연관관계에 따른 도서 관리 정책에 의해 저자가 삭제될 경우 연관된 도서도 모두 삭제됩니다.
     *
     * @param id
     */
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    private Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.AUTHOR_NOT_FOUND));
    }

    /**
     * 이메일의 고유성을 검증하기 위해 데이터베이스에 동일한 이메일이 존재하는지 확인합니다.
     *
     * @param email
     */
    private void validateEmail(String email) {
        if (authorRepository.existsByEmail(email)) {
            throw new NonUniqueValueException(GlobalErrorCode.AUTHOR_EMAIL_DUPLICATION);
        }
    }
}
