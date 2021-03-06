package net.shyshkin.study.rest.webservices.restfulwebservices.helloworld;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWorldControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @ParameterizedTest(name = "[{index}] {arguments}")
    @DisplayName("Should return proper message according to Locale (through Accept-Language header)")
    @CsvSource(
            delimiterString = ";",
            value = {
                    "en;Hello World",
                    "fr;Hello World",
                    " ;Hello World",
                    "ru;Привет, Мир!",
                    "uk;Привіт, Світ!"
            })
    void helloWorld_internationalized(String locale, String expectedMessage) {
        //given
        RequestEntity<Void> requestEntity = RequestEntity
                .get("/hello-world")
                .header(HttpHeaders.ACCEPT_LANGUAGE, locale)
                .accept(MediaType.ALL)
                .build();

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("art","123")
                .exchange(requestEntity, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedMessage);

    }

    @Test
    @DisplayName("When Accept-Language header has not been set should return message for default Locale (en_US)")
    void helloWorld_withNoAcceptLanguage() {
        //given
        RequestEntity<Void> requestEntity = RequestEntity
                .get("/hello-world")
                .accept(MediaType.ALL)
                .build();

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("art","123")
                .exchange(requestEntity, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("Hello World");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @DisplayName("Content Negotiation Test")
    @CsvSource({
            "application/xml,<HelloWorldBean><message>Hello World</message></HelloWorldBean>",
            "application/json,{\"message\":\"Hello World\"}"
    })
    void helloWorldBean_contentNegotiation(String mediaType, String expectedResponse) {
        //given
        RequestEntity<Void> requestEntity = RequestEntity
                .get("/hello-world-bean")
                .accept(MediaType.valueOf(mediaType))
                .build();

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("art","123")
                .exchange(requestEntity, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }
}