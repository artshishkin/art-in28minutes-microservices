package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilteringController.class)
class FilteringControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Fields that are annotated with JsonIgnore must be ignored")
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
    @DisplayName("Fields that present in JsonIgnoreProperties annotation must be ignored")
    void SomeBeanIgnoreJsonProperty() throws Exception {
        //when
        mockMvc.perform(get("/filtering/global"))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value3").value("value3"))
                .andExpect(jsonPath("$.value1").doesNotExist())
                .andExpect(jsonPath("$.value2").doesNotExist());
    }
}