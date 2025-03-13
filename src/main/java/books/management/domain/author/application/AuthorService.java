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

    public void create(AuthorRequestDto request) {
        validateEmail(request.getEmail());
        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

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

    public void updateAuthorDetails(Long id, AuthorRequestDto request) {
        validateEmail(request.getEmail());
        Author author = findById(id);
        author.update(request.getName(), request.getEmail());
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    private Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.AUTHOR_NOT_FOUND));
    }

    private void validateEmail(String email) {
        if (authorRepository.existsByEmail(email)) {
            throw new NonUniqueValueException(GlobalErrorCode.AUTHOR_EMAIL_DUPLICATION);
        }
    }
}
