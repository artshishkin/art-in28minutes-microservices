package net.shyshkin.study.microservices.currencyexchangeservice.service;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.currencyexchangeservice.exceptions.CurrencyNotFoundException;
import net.shyshkin.study.microservices.currencyexchangeservice.model.CurrencyExchange;
import net.shyshkin.study.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaCurrencyExchangeService implements CurrencyExchangeService {

    private final CurrencyExchangeRepository repository;

    @Override
    public CurrencyExchange find(String from, String to) {
        return repository.findByFromAndTo(from, to)
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Exchange not found: from `%s` to `%s`", from, to)));
    }
}
