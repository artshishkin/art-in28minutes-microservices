package net.shyshkin.study.rest.webservices.restfulwebservices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.ExceptionResponse;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserResourceMockMvcTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    void createNewUser_whenOK_mockUser() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON)
                .content(userJson))

                //then
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, CoreMatchers.notNullValue()));
    }

    @Test
    void createNewUser_whenOK_userArt_credentialsThroughHeader() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        String credentials = "art:123";
        String credentials64 = Base64.getEncoder().encodeToString(credentials.getBytes());

        //when
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON)
                .content(userJson)
                .header(AUTHORIZATION, "Basic " + credentials64))

                //then
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, CoreMatchers.notNullValue()));
    }

    @Test
    void createNewUser_whenOK_userArt_credentialsPostProcessor() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        mockMvc.perform(
                post("/users")
                        .with(httpBasic("art", "123"))
                        .contentType(APPLICATION_JSON)
                        .content(userJson))

                //then
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, CoreMatchers.notNullValue()));
    }

    @Test
    void createNewUser_whenOK_userWrong_credentialsPostProcessor() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        mockMvc.perform(
                post("/users")
                        .with(httpBasic("wrong", "123"))
                        .contentType(APPLICATION_JSON)
                        .content(userJson))

                //then
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(LOCATION))
                .andExpect(content().string(""));
    }

    @Test
    @WithAnonymousUser
    void createNewUser_whenOK_userAnonymous() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON)
                .content(userJson))

                //then
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(LOCATION))
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser
    void createNewUser_whenNameNotValid() throws Exception {
        //given
        User newUser = User.builder()
                .name("n")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        String jsonResponse = mockMvc.perform(
                post("/users")
                        .content(userJson)
                        .contentType(APPLICATION_JSON))

                //then
                .andExpect(status().isBadRequest())
                .andExpect(header().doesNotExist(LOCATION))
                .andReturn().getResponse().getContentAsString();

        ExceptionResponse response = objectMapper.readValue(jsonResponse, ExceptionResponse.class);
        assertThat(response)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("message", "Validation failed")
                .satisfies(exceptionResponse ->
                        assertThat(exceptionResponse.getDetails())
                                .contains("Field error in object 'user' on field 'name'", "Size.user.name", "Name must have from 2 to 255 characters")
                                .doesNotContain("birth"));
    }


    @Test
    @WithMockUser
    void createNewUser_whenDateNotValid() throws Exception {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().plusYears(2))
                .build();
        String userJson = objectMapper.writeValueAsString(newUser);

        //when
        String jsonResponse = mockMvc.perform(
                post("/users")
                        .content(userJson)
                        .contentType(APPLICATION_JSON))

                //then
                .andExpect(status().isBadRequest())
                .andExpect(header().doesNotExist(LOCATION))
                .andReturn().getResponse().getContentAsString();

        ExceptionResponse response = objectMapper.readValue(jsonResponse, ExceptionResponse.class);
        assertThat(response)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("message", "Validation failed")
                .satisfies(exceptionResponse ->
                        assertThat(exceptionResponse.getDetails())
                                .contains("Field error in object 'user' on field 'birthDate'", "Past.user.birthDate")
                                .doesNotContain("'name'", "Size"));
    }
}