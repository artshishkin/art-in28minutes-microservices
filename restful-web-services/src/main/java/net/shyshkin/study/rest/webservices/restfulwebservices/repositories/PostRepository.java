package net.shyshkin.study.rest.webservices.restfulwebservices.repositories;

import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
