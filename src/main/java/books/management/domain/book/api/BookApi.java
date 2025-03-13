package books.management.domain.book.api;

import books.management.domain.book.application.BookService;
import books.management.domain.book.dto.request.BookRequestDto;
import books.management.domain.book.dto.response.BookResponseDto;
import books.management.global.common.response.ApiResponse;
import books.management.global.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "도서 생성 API",
            description = """
                    새 도서를 생성하는 API 입니다.
                    - 제목, 설명, ISBN, 출판일, 저자 ID를 입력 받아 저장합니다.
                    - 제목, ISBN, 출판일, 저자 ID는 필수 입력값입니다.
                    - ISBN은 중복일 수 없으며, ISBN-10 규칙을 따라야 합니다.
                        - 10자리 숫자로 구성되며, 0으로 끝나야 합니다.
                        - 앞 2자리 숫자는 10~90 사이의 숫자여야 합니다.""")
    public ApiResponse<Void> create(@RequestBody @Valid BookRequestDto request) {
        bookService.create(request);
        return ApiResponse.of(ResponseCode.CREATED);
    }

    @GetMapping
    @Operation(summary = "도서 목록 조회 API",
            description = """
                    저장된 모든 도서 목록을 조회하는 API 입니다.""")
    public ApiResponse<List<BookResponseDto>> findAll() {
        return ApiResponse.of(ResponseCode.GET, bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "도서 상세 조회 API",
            description = """
                    해당 id를 가진 도서의 상세 정보를 조회하는 API 입니다.""")
    public ApiResponse<BookResponseDto> findById(@PathVariable Long id) {
        return ApiResponse.of(ResponseCode.GET, bookService.findBookById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "도서 정보 수정 API",
            description = """
                    해당 id를 가진 도서의 정보를 수정하는 API 입니다.
                    - 제목, 설명, ISBN, 출판일, 저자 ID를 입력 받아 수정합니다.
                    - 제목, ISBN, 출판일, 저자 ID는 필수 입력값입니다.
                    - ISBN은 중복일 수 없으며, ISBN-10 규칙을 따라야 합니다.
                        - 10자리 숫자로 구성되며, 0으로 끝나야 합니다.
                        - 앞 2자리 숫자는 10~90 사이의 숫자여야 합니다.""")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody @Valid BookRequestDto request) {
        bookService.updateBookDetails(id, request);
        return ApiResponse.of(ResponseCode.UPDATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "도서 삭제 API",
            description = """
                    해당 id를 가진 도서를 삭제하는 API 입니다.""")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ApiResponse.of(ResponseCode.DELETED);
    }
}
