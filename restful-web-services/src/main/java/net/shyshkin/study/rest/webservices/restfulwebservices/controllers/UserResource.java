package net.shyshkin.study.rest.webservices.restfulwebservices.controllers;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import net.shyshkin.study.rest.webservices.restfulwebservices.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User retrieveUser(@PathVariable Integer id) {
        return userService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody User user) {
        user.setId(null);
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .pathSegment("{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }
}
