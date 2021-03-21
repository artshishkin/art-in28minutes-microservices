package net.shyshkin.study.rest.webservices.restfulwebservices.bootstrap;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import net.shyshkin.study.rest.webservices.restfulwebservices.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Profile("static-resources")
public class BootstrapData implements CommandLineRunner {

    private final UserService userService;

    private static final int USERS_COUNT = 3;
    private static final int POSTS_PER_USER_COUNT = 4;


    @Override
    public void run(String... args) throws Exception {
        bootstrapUserData();
    }

    private Post createStubPost(int id) {
        return Post.builder().id(id).title("title" + id).content("Content " + id).build();
    }

    private User createStubUser(int id) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        LocalDate birthDate = LocalDate.now()
                .minusDays(random.nextInt(30))
                .minusMonths(random.nextInt(12))
                .minusYears(random.nextInt(40));

        User user = User.builder().name("name" + id)
                .birthDate(birthDate)
                .build();

        List<Post> userPosts = IntStream.rangeClosed(1, POSTS_PER_USER_COUNT)
                .mapToObj(i -> createStubPost((id - 1) * POSTS_PER_USER_COUNT + i))
                .peek(post -> post.setUser(user))
                .collect(Collectors.toList());
        user.getPosts().addAll(userPosts);

        return user;
    }

    private void bootstrapUserData() {
        IntStream
                .rangeClosed(1, USERS_COUNT)
                .mapToObj(this::createStubUser)
                .forEach(userService::save);
    }
}
