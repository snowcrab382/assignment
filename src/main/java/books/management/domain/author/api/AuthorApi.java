package books.management.domain.author.api;

import books.management.domain.author.application.AuthorService;
import books.management.domain.author.dto.request.AuthorRequestDto;
import books.management.domain.author.dto.response.AuthorResponseDto;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorService authorService;

    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid AuthorRequestDto request) {
        authorService.create(request);
        return ApiResponse.of(ResponseCode.CREATED);
    }

    @GetMapping
    public ApiResponse<List<AuthorResponseDto>> findAll() {
        return ApiResponse.of(ResponseCode.GET, authorService.findAllAuthor());
    }

    @GetMapping("/{id}")
    public ApiResponse<AuthorResponseDto> findById(@PathVariable Long id) {
        return ApiResponse.of(ResponseCode.GET, authorService.findAuthorById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody @Valid AuthorRequestDto request) {
        authorService.updateAuthorDetails(id, request);
        return ApiResponse.of(ResponseCode.UPDATED);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ApiResponse.of(ResponseCode.DELETED);
    }

}
