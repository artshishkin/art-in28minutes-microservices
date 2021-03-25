package net.shyshkin.study.microservices.currencyexchangeservice.controller;

import net.shyshkin.study.microservices.currencyexchangeservice.model.CurrencyExchange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeControllerRestTemplateTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void retrieveExchangeValue_whenPresent() {

        //when
        ResponseEntity<CurrencyExchange> responseEntity = restTemplate
                .getForEntity("/currency-exchange/from/{from}/to/{to}", CurrencyExchange.class, "USD", "UAH");

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("from", "USD")
                .hasFieldOrPropertyWithValue("to", "UAH")
                .satisfies(exchange -> assertAll(
                        () -> assertThat(exchange.getConversionMultiple()).isNotNull(),
                        () -> assertThat(exchange.getEnvironment()).isNotEmpty()
                ));
    }

    @Test
    void retrieveExchangeValue_whenAbsent() {

        //when
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate
                .exchange(
                        "/currency-exchange/from/{from}/to/{to}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        },
                        "AbsentFrom", "AbsentTo");

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody())
                .hasFieldOrPropertyWithValue("message", "Exchange not found: from `AbsentFrom` to `AbsentTo`")
                .hasFieldOrPropertyWithValue("error", "Not Found")
                .hasFieldOrPropertyWithValue("path", "/currency-exchange/from/AbsentFrom/to/AbsentTo")
                .hasFieldOrPropertyWithValue("status", 404)
                .hasFieldOrProperty("timestamp")
        ;
    }
}