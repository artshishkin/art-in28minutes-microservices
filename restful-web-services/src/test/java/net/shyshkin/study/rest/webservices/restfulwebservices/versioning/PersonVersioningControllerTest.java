package net.shyshkin.study.rest.webservices.restfulwebservices.versioning;

import org.junit.jupiter.api.Test;
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

    @Test
    void getPersonV1() throws Exception {

        //when
        mockMvc.perform(get("/v1/person")
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json("{\"name\":\"Art Shyshkin\"}")));
    }

    @Test
    void getPersonV2() throws Exception {

        //when
        mockMvc.perform(get("/v2/person")
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().json("{\"name\":{\"firstName\":\"Art\",\"lastName\":\"Shyshkin\"}}")));
    }
}