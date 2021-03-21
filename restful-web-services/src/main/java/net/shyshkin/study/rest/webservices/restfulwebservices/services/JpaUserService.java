package net.shyshkin.study.rest.webservices.restfulwebservices.services;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.PostNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import net.shyshkin.study.rest.webservices.restfulwebservices.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile("!static-resources")
@RequiredArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOne(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id `%s` not found", id)));
    }

    @Override
    public User deleteById(int id) {
        User user = findOne(id);
        userRepository.delete(user);
        return null;
    }

    @Override
    public List<Post> getAllPosts(int userId) {
        return findOne(userId).getPosts();
    }

    @Override
    public Post savePost(Integer userId, Post post) {
        User user = findOne(userId);
        post.setUser(user);
        List<Post> initialPosts = user.getPosts();
        Set<Integer> initialIndices = initialPosts.stream().map(Post::getId).collect(Collectors.toSet());
        initialPosts.add(post);
        User savedUser = userRepository.save(user);
        return savedUser.getPosts()
                .stream()
                .filter(post1 -> !initialIndices.contains(post1.getId()))
                .findAny()
                .orElse(post);
    }

    @Override
    public Post findPost(int userId, int postId) {
        User user = findOne(userId);
        for (Post post : user.getPosts()) {
            if (post.getId() == postId)
                return post;
        }
        throw new PostNotFoundException(String.format("Post with id `%d` is not found for user `%d`", postId, userId));
    }
}
