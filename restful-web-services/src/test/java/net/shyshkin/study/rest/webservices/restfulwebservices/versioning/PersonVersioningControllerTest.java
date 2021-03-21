package net.shyshkin.study.rest.webservices.restfulwebservices.versioning;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonVersioningController.class)
class PersonVersioningControllerTest {

    @Autowired
    MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource(
            delimiterString = ";",
            value = {
                    "/v1/person;{\"name\":\"Art Shyshkin\"}",
                    "/v2/person;{\"name\":{\"firstName\":\"Art\",\"lastName\":\"Shyshkin\"}}",
            })
    void versioningThroughPath(String endpointUrl, String expectedJson) throws Exception {

        //when
        mockMvc.perform(get(endpointUrl)
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvSource(
            delimiterString = ";",
            value = {
                    "1;{\"name\":\"Art Shyshkin\"}",
                    "2;{\"name\":{\"firstName\":\"Art\",\"lastName\":\"Shyshkin\"}}",
            })
    void versioningThroughParam(String version, String expectedJson) throws Exception {

        //when
        mockMvc.perform(get("/person/param")
                .param("version", version)
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvSource(
            delimiterString = ";",
            value = {
                    "1;{\"name\":\"Art Shyshkin\"}",
                    "2;{\"name\":{\"firstName\":\"Art\",\"lastName\":\"Shyshkin\"}}",
            })
    void versioningThroughHeader(String version, String expectedJson) throws Exception {

        //when
        mockMvc.perform(get("/person/header")
                .header("X-API-VERSION", version)
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json(expectedJson)));
    }

    @ParameterizedTest
    @CsvSource(
            delimiterString = ";",
            value = {
                    "application/vnd.company.app-v1+json;{\"name\":\"Art Shyshkin\"}",
                    "application/vnd.company.app-v2+json;{\"name\":{\"firstName\":\"Art\",\"lastName\":\"Shyshkin\"}}",
            })
    void versioningThroughAcceptHeader(String acceptMediaType, String expectedJson) throws Exception {

        //when
        mockMvc.perform(get("/person/produces")
                .accept(acceptMediaType))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json(expectedJson)));
    }

}