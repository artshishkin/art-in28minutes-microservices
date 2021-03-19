package net.shyshkin.study.rest.webservices.restfulwebservices.services;

import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.PostNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDaoService implements UserService {

    private static final Map<Integer, User> userRepository = new HashMap<>();
    private static Integer lastId = 0;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public User save(User user) {
        Integer id = user.getId();
        if (id == null) {
            id = ++lastId;
            user.setId(id);
        }
        userRepository.put(id, user);
        return user;
    }

    @Override
    public User findOne(int id) {
        User user = userRepository.get(id);
        if (user == null) throw new UserNotFoundException(String.format("User with id `%s` not found", id));
        return user;
    }

    @Override
    public User deleteById(int id) {
        User user = findOne(id);
        return userRepository.remove(user.getId());
    }

    @Override
    public List<Post> getAllPosts(int userId) {
        return findOne(userId).getPosts();
    }

    @Override
    public Post savePost(Integer userId, Post post) {
        User user = findOne(userId);
        post.setUser(user);
        post.setId(newId());
        user.getPosts().add(post);
        return post;
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

    private int newId() {
        int lastIndex = userRepository.values()
                .stream()
                .flatMap(user -> user.getPosts().stream())
                .mapToInt(Post::getId)
                .max()
                .orElse(0);
        return lastIndex + 1;
    }
}
