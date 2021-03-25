package net.shyshkin.study.microservices.currencyexchangeservice.service;

import net.shyshkin.study.microservices.currencyexchangeservice.model.CurrencyExchange;

public interface CurrencyExchangeService {

    CurrencyExchange find(String from, String to);

}
