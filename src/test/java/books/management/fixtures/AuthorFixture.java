package books.management.fixtures;

import books.management.domain.author.domain.Author;

public class AuthorFixture {

    public static Author createAuthor(String name, String email) {
        return Author
                .builder()
                .name(name)
                .email(email)
                .build();
    }
}
