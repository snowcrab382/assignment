package books.management.domain.author.api;

import books.management.domain.author.application.AuthorService;
import books.management.domain.author.domain.Author;
import books.management.domain.author.dto.request.AuthorCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorService authorService;

    @PostMapping
    public void create(@RequestBody AuthorCreateRequest request) {
        authorService.create(request);
    }

    @GetMapping
    public List<Author> findAll() {
        return authorService.findAllAuthor();
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id) {
        return authorService.findAuthorById(id);
    }

}
