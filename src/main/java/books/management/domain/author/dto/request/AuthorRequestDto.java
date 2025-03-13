package books.management.domain.author.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorRequestDto {

    private String name;
    private String email;

    public AuthorRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
