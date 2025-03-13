package books.management.domain.book.api;

import books.management.domain.book.application.BookService;
import books.management.domain.book.dto.request.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
}
