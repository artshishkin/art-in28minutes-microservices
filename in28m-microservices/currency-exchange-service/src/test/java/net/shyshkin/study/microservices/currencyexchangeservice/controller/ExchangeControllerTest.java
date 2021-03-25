package net.shyshkin.study.microservices.currencyexchangeservice.controller;

import net.shyshkin.study.microservices.currencyexchangeservice.exceptions.CurrencyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
    }

    @Test
    void retrieveExchangeValue_whenPresent() throws Exception {
        //when
        mockMvc.perform(get("/currency-exchange/from/{from}/to/{to}", "USD", "UAH")
                .accept(APPLICATION_JSON))

                //then
                .andExpect(
                        matchAll(
                                status().isOk(),
                                jsonPath("$.from").value("USD"),
                                jsonPath("$.to").value("UAH"),
                                jsonPath("$.conversionMultiple").isNumber(),
                                jsonPath("$.environment").isNotEmpty()
                        ));
    }

    @Test
    void retrieveExchangeValue_whenAbsent() throws Exception {

        //when
        Exception resolvedException = mockMvc.perform(get("/currency-exchange/from/{from}/to/{to}", "AbsentFrom", "AbsentTo")
                .accept(APPLICATION_JSON))

                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException();

        assertThat(resolvedException)
                .isInstanceOf(CurrencyNotFoundException.class)
                .hasMessage("Exchange not found: from `AbsentFrom` to `AbsentTo`");
    }
}