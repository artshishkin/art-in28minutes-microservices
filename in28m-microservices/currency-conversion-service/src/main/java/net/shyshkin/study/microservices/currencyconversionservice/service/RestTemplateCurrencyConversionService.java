package net.shyshkin.study.microservices.currencyconversionservice.service;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Primary
@Profile("restTemplate")
@RequiredArgsConstructor
public class RestTemplateCurrencyConversionService implements CurrencyConversionService {

    private final Environment environment;
    private final RestTemplate restTemplate;

    @Override
    public CurrencyConversion retrieveCurrencyConversionValue(String from, String to, BigDecimal quantity) {

        String host = environment.getProperty("exchange-service.host");
        String path = environment.getProperty("exchange-service.path");
        String exchangeServiceUrl = host + path;

        CurrencyConversion currencyConversion = restTemplate.getForObject(exchangeServiceUrl, CurrencyConversion.class, from, to);
        if (currencyConversion == null)
            throw new RuntimeException(String.format("Can not retrieve Currency Exchange Object for `%s` -> `%s`", from, to));

        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        BigDecimal totalCalcAmount = conversionMultiple.multiply(quantity);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(totalCalcAmount);
        return currencyConversion;
    }
}
