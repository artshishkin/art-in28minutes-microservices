package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class ExceptionCircuitBreakerService implements CircuitBreakerService {

    @Override
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public String retrieveSomeData() {
        log.debug("Sample API call for any method that throws Exception");
        String fakeResult = "Fake Result";
        if (true) throw new RuntimeException("retrieveSomeData method exception");
        log.debug("Result is {}", fakeResult);
        return fakeResult;
    }

    public String hardcodedResponse(Exception exception) {
        return "fallback-response";
    }

}
