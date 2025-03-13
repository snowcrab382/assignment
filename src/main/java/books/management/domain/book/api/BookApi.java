package books.management.domain.book.api;

import books.management.domain.book.application.BookService;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import books.management.global.common.response.ApiResponse;
import books.management.global.common.response.ResponseCode;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ApiResponse<Void> create(@RequestBody @Valid BookRequestDto request) {
        bookService.create(request);
        return ApiResponse.of(ResponseCode.CREATED);
    }

    @GetMapping
    public ApiResponse<List<BookResponseDto>> findAll() {
        return ApiResponse.of(ResponseCode.GET, bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponseDto> findById(@PathVariable Long id) {
        return ApiResponse.of(ResponseCode.GET, bookService.findBookById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody @Valid BookRequestDto request) {
        bookService.updateBookDetails(id, request);
        return ApiResponse.of(ResponseCode.UPDATED);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ApiResponse.of(ResponseCode.DELETED);
    }
}
