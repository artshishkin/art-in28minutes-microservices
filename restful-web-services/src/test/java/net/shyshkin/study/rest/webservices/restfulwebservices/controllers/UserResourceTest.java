package net.shyshkin.study.rest.webservices.restfulwebservices.controllers;

import net.shyshkin.study.rest.webservices.restfulwebservices.exceptions.ExceptionResponse;
import net.shyshkin.study.rest.webservices.restfulwebservices.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserResourceTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createNewUser_whenOK() {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        URI uri = URI.create("/users");
//        URI uri = MvcUriComponentsBuilder.fromMethodName(UserResource.class, "createNewUser", newUser).build().toUri();
//        URI uri = MvcUriComponentsBuilder.fromController(UserResource.class).build().toUri();

        //when
        ResponseEntity<Void> responseEntity = restTemplate
                .withBasicAuth("art", "123")
                .postForEntity(uri, newUser, Void.TYPE);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("When wrong username or password must be UNAUTHORIZED")
    @ValueSource(strings = {"art", "wrongName"})
    void createNewUser_whenWrongPassword(String userName) {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        URI uri = URI.create("/users");

        //when
        ResponseEntity<Void> responseEntity = restTemplate
                .withBasicAuth(userName, "wrongPassword")
                .postForEntity(uri, newUser, Void.TYPE);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void createNewUser_whenNameNotValid() {
        //given
        User newUser = User.builder()
                .name("n")
                .birthDate(LocalDate.now().minusYears(2))
                .build();
        URI uri = URI.create("/users");

        //when
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate
                .withBasicAuth("art", "123")
                .postForEntity(uri, newUser, ExceptionResponse.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getHeaders().getLocation()).isNull();
        assertThat(responseEntity.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("message", "Validation failed")
                .satisfies(exceptionResponse ->
                        assertThat(exceptionResponse.getDetails())
                                .contains("Field error in object 'user' on field 'name'", "Size.user.name", "Name must have from 2 to 255 characters")
                                .doesNotContain("birth"));
    }

    @Test
    void createNewUser_whenDateNotValid() {
        //given
        User newUser = User.builder()
                .name("name")
                .birthDate(LocalDate.now().plusYears(2))
                .build();
        URI uri = URI.create("/users");

        //when
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate
                .withBasicAuth("art", "123")
                .postForEntity(uri, newUser, ExceptionResponse.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getHeaders().getLocation()).isNull();
        assertThat(responseEntity.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("message", "Validation failed")
                .satisfies(exceptionResponse ->
                        assertThat(exceptionResponse.getDetails())
                                .contains("Field error in object 'user' on field 'birthDate'", "Past.user.birthDate")
                                .doesNotContain("'name'", "Size"));
    }
}