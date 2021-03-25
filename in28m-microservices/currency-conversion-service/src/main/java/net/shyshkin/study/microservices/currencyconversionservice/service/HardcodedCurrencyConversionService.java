package net.shyshkin.study.microservices.currencyconversionservice.service;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class HardcodedCurrencyConversionService implements CurrencyConversionService {

    private final Environment environment;

    @Override
    public CurrencyConversion retrieveCurrencyConversionValue(String from, String to, BigDecimal quantity) {

        BigDecimal conversionMultiple = BigDecimal.valueOf(123);
        BigDecimal totalCalcAmount = conversionMultiple.multiply(quantity);

        String serverPort = environment.getProperty("local.server.port");
        String appName = environment.getProperty("spring.application.name");

        return CurrencyConversion.builder()
                .id(20001L)
                .from(from)
                .to(to)
                .quantity(quantity)
                .conversionMultiple(conversionMultiple)
                .totalCalculatedAmount(totalCalcAmount)
                .environment(serverPort + " " + appName)
                .build();
    }
}
