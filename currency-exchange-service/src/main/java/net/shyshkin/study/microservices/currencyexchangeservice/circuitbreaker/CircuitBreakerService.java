package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;

public interface CircuitBreakerService {
    String retrieveSomeData();

    String rateLimiter();
}
