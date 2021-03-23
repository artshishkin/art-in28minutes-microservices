package net.shyshkin.study.microservices.currencyexchangeservice.circuitbreaker;


import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerService {

    public String retrieveSomeData() {
        return "Sample API";
    }

}
