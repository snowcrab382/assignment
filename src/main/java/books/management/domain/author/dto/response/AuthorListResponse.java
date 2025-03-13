package books.management.domain.author.dto.response;

public class AuthorListResponse {

    private Long id;
    private String name;
    private String email;

    public AuthorListResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
