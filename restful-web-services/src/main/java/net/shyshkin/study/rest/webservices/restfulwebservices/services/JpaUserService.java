package net.shyshkin.study.rest.webservices.restfulwebservices.services;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.PostNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import net.shyshkin.study.rest.webservices.restfulwebservices.repositories.PostRepository;
import net.shyshkin.study.rest.webservices.restfulwebservices.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("!static-resources")
@RequiredArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

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
    @Transactional
    public Post savePost(Integer userId, Post post) {
        User user = findOne(userId);
        user.addPost(post);
        return postRepository.save(post);
    }

    @Override
    public Post findPost(int userId, int postId) {

        return postRepository
                .findById(postId)
                .filter(post -> post.getUser().getId() == userId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id `%d` is not found for user `%d`", postId, userId)));
    }
}
