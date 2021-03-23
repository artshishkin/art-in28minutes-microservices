package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestTemplateCircuitBreakerService implements CircuitBreakerService {

    private final RestTemplate restTemplate;

    public RestTemplateCircuitBreakerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Retry(name = "sample-api")
    public String retrieveSomeData() {
        log.debug("Sample API call for nonExisting REST service");
        String fakeResult = restTemplate.getForObject("http://localhost:4321/non-existing-url", String.class);
        log.debug("Result is {}", fakeResult);
        return fakeResult;
    }

}
