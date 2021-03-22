package net.shyshkin.study.microservices.currencyconversionservice.service;

import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    CurrencyConversion retrieveCurrencyConversionValue(String from, String to, BigDecimal quantity);
}
