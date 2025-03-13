package books.management.domain.author.application;

import books.management.domain.author.dao.AuthorRepository;
import books.management.domain.author.domain.Author;
import books.management.domain.author.dto.request.AuthorCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public void create(AuthorCreateRequest request) {
        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public List<Author> findAllAuthor() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findAuthorById(Long id) {
        return findById(id);
    }

    private Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
