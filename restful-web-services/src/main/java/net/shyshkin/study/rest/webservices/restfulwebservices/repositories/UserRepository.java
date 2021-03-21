package net.shyshkin.study.rest.webservices.restfulwebservices.repositories;

import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
