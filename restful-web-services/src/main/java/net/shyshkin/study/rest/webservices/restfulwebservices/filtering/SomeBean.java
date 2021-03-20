package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SomeBean {

    private String value1;

    @JsonIgnore
    private String value2;

    @JsonIgnore
    private String value3;
}
