package net.shyshkin.study.microservices.currencyconversionservice.config;

import net.shyshkin.study.microservices.currencyconversionservice.service.CurrencyExchangeServiceFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = CurrencyExchangeServiceFeignClient.class)
public class FeignConfig {
}
