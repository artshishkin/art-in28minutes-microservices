package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"value1","value2"})
public class SomeBeanIgnoreJsonProperty {

    private String value1;
    private String value2;
    private String value3;
}
