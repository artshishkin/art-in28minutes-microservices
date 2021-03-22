package net.shyshkin.study.microservices.currencyexchangeservice.controller;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.currencyexchangeservice.model.CurrencyExchange;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        String port = environment.getProperty("local.server.port");
        String appName = environment.getProperty("spring.application.name");
        return CurrencyExchange.builder()
                .id(10001L)
                .from(from)
                .to(to)
                .conversionMultiple(BigDecimal.valueOf(65.0))
                .environment(String.format("%s %s", port, appName))
                .build();
    }
}
