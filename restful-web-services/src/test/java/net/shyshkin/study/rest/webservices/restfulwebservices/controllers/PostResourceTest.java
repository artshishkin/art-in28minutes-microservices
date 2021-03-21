package net.shyshkin.study.rest.webservices.restfulwebservices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.Post;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import net.shyshkin.study.rest.webservices.restfulwebservices.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
class PostResourceTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userRepository.deleteAll();

    }

    private User createStubUserWithPosts(int postCount) {

        User user = User.builder()
                .name("Name:" + UUID.randomUUID())
                .birthDate(LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(20)))
                .build();

        for (int i = 0; i < postCount; i++) {
            Post post = Post.builder()
                    .title("Title:" + UUID.randomUUID())
                    .content("Content:" + UUID.randomUUID())
                    .build();
            user.addPost(post);
        }

        return userRepository.save(user);
    }


    @Test
    void retrieveAllPosts() throws Exception {
        //given
        User user = createStubUserWithPosts(3);
        Post post = user.getPosts().get(0);
        Integer userId = user.getId();

        //when
        mockMvc.perform(
                get("/users/{userId}/posts", userId)
                        .accept(APPLICATION_JSON))
                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                jsonPath("$", IsCollectionWithSize.hasSize(3)),
                                jsonPath("$.[0].content").value(CoreMatchers.containsString("Content")),
                                jsonPath("$.[1].content").value(CoreMatchers.containsString("Content")),
                                jsonPath("$.[2].content").value(CoreMatchers.containsString("Content"))
                        ));
    }

    @Test
    void retrievePostById() throws Exception {
        //given
        User user = createStubUserWithPosts(5);
        Post post = user.getPosts().get(0);
        Integer userId = user.getId();
        Integer postId = post.getId();

        //when
        mockMvc.perform(
                get("/users/{userId}/posts/{postId}", userId, postId)
                        .accept(APPLICATION_JSON))
                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                jsonPath("$.title").value(post.getTitle()),
                                jsonPath("$.content").value(post.getContent())
                        ));
    }
}