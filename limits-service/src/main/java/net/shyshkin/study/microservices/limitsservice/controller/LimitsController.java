package net.shyshkin.study.microservices.limitsservice.controller;

import lombok.RequiredArgsConstructor;
import net.shyshkin.study.microservices.limitsservice.config.ApplicationConfiguration;
import net.shyshkin.study.microservices.limitsservice.domain.Limits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("limits")
@RequiredArgsConstructor
public class LimitsController {

    private final ApplicationConfiguration appConfig;

    @GetMapping
    public Limits retrieveLimits() {
        return new Limits(appConfig.getMinimum(), appConfig.getMaximum());
    }
}
