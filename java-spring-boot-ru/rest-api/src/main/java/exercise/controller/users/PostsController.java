package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users/{id}/posts")
public class PostsController {

    List<Post> posts = Data.getPosts();

    @GetMapping
    public List<Post> getUserPosts(@PathVariable int id) {
        return posts.stream().filter(post -> post.getUserId() == id).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
