package net.shyshkin.study.microservices.currencyconversionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;
import net.shyshkin.study.microservices.currencyconversionservice.service.CurrencyConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyConversionService service;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyConversionValue(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        log.debug("retrieveCurrencyConversionValue was called from: `{}`, to: `{}`, quantity: {}", from, to, quantity);
        return service.retrieveCurrencyConversionValue(from, to, quantity);
    }

}
