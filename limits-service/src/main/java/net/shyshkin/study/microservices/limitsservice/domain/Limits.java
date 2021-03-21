package net.shyshkin.study.microservices.limitsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Limits {
    private int minimum;
    private int maximum;
}
