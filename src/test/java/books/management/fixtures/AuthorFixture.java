package books.management.fixtures;

import books.management.domain.author.domain.Author;
import books.management.domain.author.dto.request.AuthorRequestDto;
import books.management.domain.author.dto.response.AuthorResponseDto;

public class AuthorFixture {

    public static Author createAuthor(String name, String email) {
        return Author
                .builder()
                .name(name)
                .email(email)
                .build();
    }

    public static AuthorRequestDto createAuthorRequestDto(String name, String email) {
        return AuthorRequestDto
                .builder()
                .name(name)
                .email(email)
                .build();
    }

    public static AuthorResponseDto createAuthorResponseDto(Long id, String name, String email) {
        return AuthorResponseDto
                .builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
    }
}
