package net.shyshkin.study.microservices.limitsservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "limits-service")
@Setter
@Getter
public class ApplicationConfiguration {

    private int minimum;
    private int maximum;

}
