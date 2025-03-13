package books.management.domain.author.dto.response;

import books.management.domain.author.domain.Author;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorResponseDto {

    private Long id;
    private String name;
    private String email;

    @Builder
    private AuthorResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static AuthorResponseDto from(Author author) {
        return AuthorResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .build();
    }

}
