package net.shyshkin.study.microservices.currencyexchangeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.study.microservices.currencyexchangeservice.model.CurrencyExchange;
import net.shyshkin.study.microservices.currencyexchangeservice.service.CurrencyExchangeService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final Environment environment;
    private final CurrencyExchangeService service;

    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}", produces = APPLICATION_JSON_VALUE)
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        log.debug("retrieveExchangeValue was called with: from `{}` to `{}`", from, to);

        String port = environment.getProperty("local.server.port");
        String appName = environment.getProperty("spring.application.name");
        String hostname = environment.getProperty("HOSTNAME");
        String version = "v1-k8s";

        CurrencyExchange currencyExchange = service.find(from, to);
        currencyExchange.setEnvironment(String.format("%s %s %s %s ", port, appName, version, hostname));
        return currencyExchange;
    }
}
