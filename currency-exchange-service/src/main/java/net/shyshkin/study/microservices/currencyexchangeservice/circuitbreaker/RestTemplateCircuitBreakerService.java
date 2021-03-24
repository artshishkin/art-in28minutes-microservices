package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String retrieveSomeData() {
        log.debug("Sample API call for nonExisting REST service");
        String fakeResult = restTemplate.getForObject("http://localhost:4321/non-existing-url", String.class);
        log.debug("Result is {}", fakeResult);
        return fakeResult;
    }

    public String hardcodedResponse(Exception exception) {
        return "fallback-response";
    }

    @Override
    @RateLimiter(name = "default", fallbackMethod = "exceededRateResponse")
    public String rateLimiter() {
        log.debug("Calling Request Limit OK");
        return "request rate OK";
    }

    @Override
    @Bulkhead(name = "default")
    public String bulkHead() {
        log.debug("Calling bulkHead");
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "bulkHead method";
    }

    public String exceededRateResponse(Exception exception) {
        return "request rate EXCEEDED";
    }

}
