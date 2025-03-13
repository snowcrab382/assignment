package books.management.domain.book.application;

import books.management.domain.author.application.AuthorService;
import books.management.domain.author.domain.Author;
import books.management.domain.book.dao.BookRepository;
import books.management.domain.book.domain.Book;
import books.management.domain.book.dto.request.BookRequestDto;
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
}
