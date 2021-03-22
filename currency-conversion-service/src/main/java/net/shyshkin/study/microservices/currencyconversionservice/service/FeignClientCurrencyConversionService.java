package net.shyshkin.study.microservices.currencyconversionservice.service;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
@Profile("!restTemplate")
@RequiredArgsConstructor
public class FeignClientCurrencyConversionService implements CurrencyConversionService {

    private final CurrencyExchangeServiceFeignClient feignClient;

    @Override
    public CurrencyConversion retrieveCurrencyConversionValue(String from, String to, BigDecimal quantity) {
        CurrencyConversion currencyConversion = feignClient.retrieveExchangeValue(from, to);

        if (currencyConversion == null)
            throw new RuntimeException(String.format("Can not retrieve Currency Exchange Object for `%s` -> `%s`", from, to));

        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        BigDecimal totalCalcAmount = conversionMultiple.multiply(quantity);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(totalCalcAmount);

        return currencyConversion;
    }
}
