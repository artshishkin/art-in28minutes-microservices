package net.shyshkin.study.rest.webservices.restfulwebservices.controllers;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/posts")
public class PostResource {

    private final UserService userService;

    @GetMapping
    public List<Post> retrieveAllPosts(@PathVariable Integer userId) {
        return userService.getAllPosts(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createNewPost(@PathVariable Integer userId, @RequestBody Post post) {
        Post savedPost = userService.savePost(userId, post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .pathSegment("{postId}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{postId}")
    public Post retrieveAllPosts(@PathVariable Integer userId, @PathVariable Integer postId) {
        return userService.findPost(userId, postId);
    }

}
