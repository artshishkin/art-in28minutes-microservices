package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilteringController.class)
class FilteringControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("When GET fields that are annotated with JsonIgnore must be ignored")
    void getSomeBean() throws Exception {
        //when
        mockMvc.perform(get("/filtering/personal"))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value1").value("value1"))
                .andExpect(jsonPath("$.value2").doesNotExist())
                .andExpect(jsonPath("$.value3").doesNotExist());
    }

    @Test
    @DisplayName("When POST fields that are annotated with JsonIgnore must be ignored")
    void postSomeBean() throws Exception {
        //given
        String someBeanContent = "{\"value1\":\"hello1\",\"value2\":\"hello2\",\"value3\":\"hello3\"}";
        String expectedOutput = "SomeBean(value1=hello1, value2=null, value3=null)";

        //when
        mockMvc.perform(
                post("/filtering/personal")
                        .content(someBeanContent)
                        .contentType(MediaType.APPLICATION_JSON)
        )

                //then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutput));
    }

    @Test
    @DisplayName("When GET fields that present in JsonIgnoreProperties annotation must be ignored")
    void getSomeBeanIgnoreJsonProperty() throws Exception {
        //when
        mockMvc.perform(get("/filtering/global"))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value3").value("value3"))
                .andExpect(jsonPath("$.value1").doesNotExist())
                .andExpect(jsonPath("$.value2").doesNotExist());
    }

    @Test
    @DisplayName("When POST fields that present in JsonIgnoreProperties annotation must be ignored")
    void postSomeBeanIgnoreJsonProperty() throws Exception {
        //given
        String someBeanContent = "{\"value1\":\"hello1\",\"value2\":\"hello2\",\"value3\":\"hello3\"}";
        String expectedOutput = "SomeBeanIgnoreJsonProperty(value1=null, value2=null, value3=hello3)";

        //when
        mockMvc.perform(
                post("/filtering/global")
                        .content(someBeanContent)
                        .contentType(MediaType.APPLICATION_JSON)
        )

                //then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutput));
    }

    @Test
    @DisplayName("When GET fields that are filtered dynamically must be absent")
    void getSomeBeanDynamic() throws Exception {
        //when
        mockMvc.perform(get("/filtering/dynamic"))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value1").value("value1"))
                .andExpect(jsonPath("$.value2").value("value2"))
                .andExpect(jsonPath("$.value3").doesNotExist());
    }

    @Test
    @DisplayName("When POST - fields with Filter switched off must be present")
    void postSomeBeanDynamic() throws Exception {
        //given
        String someBeanContent = "{\"value1\":\"hello1\",\"value2\":\"hello2\",\"value3\":\"hello3\"}";
        String expectedOutput = "SomeBeanDynamic(value1=hello1, value2=hello2, value3=hello3)";

        //when
        mockMvc.perform(
                post("/filtering/dynamic")
                        .content(someBeanContent)
                        .contentType(MediaType.APPLICATION_JSON)
        )

                //then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutput));
    }

    @Test
    @DisplayName("When GET List - fields that are filtered dynamically must be absent")
    void getSomeBeanDynamicList() throws Exception {
        //when
        mockMvc.perform(get("/filtering/dynamic-list"))

                //then
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].value1").value("value11"))
                .andExpect(jsonPath("$.[0].value2").doesNotExist())
                .andExpect(jsonPath("$.[0].value3").value("value13"))

                .andExpect(jsonPath("$.[1].value1").value("value21"))
                .andExpect(jsonPath("$.[1].value2").doesNotExist())
                .andExpect(jsonPath("$.[1].value3").value("value23"));
    }
}