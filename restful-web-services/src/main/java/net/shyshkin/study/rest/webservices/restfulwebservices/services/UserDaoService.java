package net.shyshkin.study.rest.webservices.restfulwebservices.services;

import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.of;

@Service
public class UserDaoService implements UserService {

    private static final Map<Integer, User> userRepository = new HashMap<>(4);
    private static Integer lastId = 0;

    static {
        userRepository.put(1, User.builder().id(1).name("Art").birthDate(of(1983, 2, 7)).build());
        userRepository.put(2, User.builder().id(2).name("Kate").birthDate(of(1983, 2, 13)).build());
        userRepository.put(3, User.builder().id(3).name("Arina").birthDate(of(2010, 7, 23)).build());
        userRepository.put(4, User.builder().id(4).name("Nazar").birthDate(of(2016, 6, 1)).build());
        lastId = 4;
    }

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
}
