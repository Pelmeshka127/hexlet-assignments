package exercise;

import java.net.URI;
import java.rmi.server.RemoteRef;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;
import lombok.Setter;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    @Setter
    private static  List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "10") Integer limit) {
        var retPosts = posts.stream()
                .limit(limit)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(retPosts);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        var result = posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
        return result.map(post -> ResponseEntity.ok().body(post)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var result = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        result.get().setId(post.getId());
        result.get().setBody(post.getBody());
        result.get().setTitle(post.getTitle());
        return ResponseEntity.ok()
                .body(result.get());
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
