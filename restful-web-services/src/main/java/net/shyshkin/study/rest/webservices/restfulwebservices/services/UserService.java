package net.shyshkin.study.rest.webservices.restfulwebservices.services;

import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    User findOne(int id);

    User deleteById(int id);

    List<Post> getAllPosts(int userId);

    Post savePost(Integer userId, Post post);

    Post findPost(int userId, int postId);
}
