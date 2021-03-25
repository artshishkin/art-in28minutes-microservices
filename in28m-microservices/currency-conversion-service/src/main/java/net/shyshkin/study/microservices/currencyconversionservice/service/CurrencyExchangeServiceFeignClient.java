package net.shyshkin.study.microservices.currencyconversionservice.service;

import net.shyshkin.study.microservices.currencyconversionservice.config.FeignConfig;
import net.shyshkin.study.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange", url = "${exchange-service.host}", configuration = FeignConfig.class)
public interface CurrencyExchangeServiceFeignClient {

//    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    @GetMapping("${exchange-service.path}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}



