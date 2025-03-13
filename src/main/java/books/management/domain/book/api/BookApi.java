package books.management.domain.book.api;

import books.management.domain.book.application.BookService;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApi {

    private final BookService bookService;

    @PostMapping
    public void create(@RequestBody BookRequestDto request) {
        bookService.create(request);
    }

    @GetMapping
    public List<BookResponseDto> findAll() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody BookRequestDto request) {
        bookService.updateBookDetails(id, request);
    }
}
