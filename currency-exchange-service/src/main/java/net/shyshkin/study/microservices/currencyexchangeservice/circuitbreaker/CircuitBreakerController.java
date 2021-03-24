package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CircuitBreakerController {

    private final CircuitBreakerService circuitBreakerService;

    @GetMapping("/sample-api")
    public String sampleApi() {

        return circuitBreakerService.retrieveSomeData();
    }

    @GetMapping("/rate-limit")
    public String rateLimit() {
        return circuitBreakerService.rateLimiter();
    }

}
