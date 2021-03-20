package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonFilter("SomeBeanFilter")
public class SomeBeanDynamic {

    private String value1;

    private String value2;

    private String value3;
}
