package books.management.domain.author.dao;

import books.management.domain.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public boolean existsByEmail(String email);
}
