package books.management.domain.author.domain;

import books.management.domain.Book;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "author")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    @Builder
    private Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Author of(String name, String email) {
        return Author.builder()
                .name(name)
                .email(email)
                .build();
    }

}
