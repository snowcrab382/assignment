package books.management.domain.author.api;

import books.management.domain.author.application.AuthorService;
import books.management.domain.author.dto.request.AuthorRequestDto;
import books.management.domain.author.dto.response.AuthorResponseDto;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorService authorService;

    @PostMapping
    @Operation(summary = "저자 생성 API",
            description = """
                    저자를 생성하는 API 입니다.
                    - 저자 이름, 이메일을 입력 받아 저장합니다.
                    - 저자 이름, 이메일은 필수 입력값입니다.
                    - 이메일은 중복일 수 없습니다.
                    - 이메일은 규칙에 맞는 이메일 형식이어야 합니다.""")
    public ApiResponse<Void> create(@RequestBody @Valid AuthorRequestDto request) {
        authorService.create(request);
        return ApiResponse.of(ResponseCode.CREATED);
    }

    @GetMapping
    @Operation(summary = "저자 목록 조회 API",
            description = """
                    저장된 모든 저자 목록을 조회하는 API 입니다.""")
    public ApiResponse<List<AuthorResponseDto>> findAll() {
        return ApiResponse.of(ResponseCode.GET, authorService.findAllAuthor());
    }

    @GetMapping("/{id}")
    @Operation(summary = "저자 상세 조회 API",
            description = """
                    해당 id를 가진 저자의 상세 정보를 조회하는 API 입니다.""")
    public ApiResponse<AuthorResponseDto> findById(@PathVariable Long id) {
        return ApiResponse.of(ResponseCode.GET, authorService.findAuthorById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "저자 정보 수정 API",
            description = """
                    해당 id를 가진 저자의 정보를 수정하는 API 입니다.
                    - 저자 이름, 이메일을 입력 받아 수정합니다.
                    - 저자 이름, 이메일은 필수 입력값입니다.
                    - 이메일은 중복일 수 없습니다.
                    - 이메일은 규칙에 맞는 이메일 형식이어야 합니다.""")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody @Valid AuthorRequestDto request) {
        authorService.updateAuthorDetails(id, request);
        return ApiResponse.of(ResponseCode.UPDATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "저자 삭제 API",
            description = """
                    해당 id를 가진 저자를 삭제하는 API 입니다.
                    - 해당 저자가 삭제될 경우, 연관된 도서도 모두 삭제되므로 주의가 필요합니다.""")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ApiResponse.of(ResponseCode.DELETED);
    }

}
