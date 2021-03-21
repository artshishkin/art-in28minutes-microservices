package net.shyshkin.study.rest.webservices.restfulwebservices.helloworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
class HelloWorldControllerMockMvcTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

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
    void helloWorld_internationalized(String locale, String expectedMessage) throws Exception {
        //given
        if (locale == null) locale = "";

        //when
        mockMvc.perform(get("/hello-world")
                .header(HttpHeaders.ACCEPT_LANGUAGE, locale)
                .accept(MediaType.ALL))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().string(expectedMessage)));

    }

    @Test
    @DisplayName("When Accept-Language header has not been set should return message for default Locale (en_US)")
    void helloWorld_withNoAcceptLanguage() throws Exception {

        //when
        mockMvc.perform(get("/hello-world")
                .accept(MediaType.ALL))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().string("Hello World")));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @DisplayName("Content Negotiation Test")
    @CsvSource({
            "application/xml,<HelloWorldBean><message>Hello World</message></HelloWorldBean>",
            "application/json,{\"message\":\"Hello World\"}"
    })
    void helloWorldBean_contentNegotiation(String mediaType, String expectedResponse) throws Exception {

        //when
        mockMvc.perform(get("/hello-world-bean")
                .accept(mediaType))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().string(expectedResponse)));
    }
}