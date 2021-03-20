package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filtering")
public class FilteringController {

    @GetMapping("personal")
    public SomeBean getSomeBean() {
        return new SomeBean("value1", "value2", "value3");
    }

    @PostMapping("personal")
    public String postSomeBean(@RequestBody SomeBean someBean) {
        return someBean.toString();
    }

    @GetMapping("global")
    public SomeBeanIgnoreJsonProperty getSomeBeanIgnoreJsonProperty() {
        return new SomeBeanIgnoreJsonProperty("value1", "value2", "value3");
    }

    @PostMapping("global")
    public String postSomeBeanIgnoreJsonProperty(@RequestBody SomeBeanIgnoreJsonProperty someBeanIgnoreJsonProperty) {
        return someBeanIgnoreJsonProperty.toString();
    }

    @GetMapping("dynamic")
    public MappingJacksonValue getSomeBeanDynamic() {
        SomeBeanDynamic someBeanDynamic = new SomeBeanDynamic("value1", "value2", "value3");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value1", "value2");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeanDynamic);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("dynamic-list")
    public MappingJacksonValue getSomeBeanDynamicList() {
        List<SomeBeanDynamic> someBeanDynamicList = List.of(
                new SomeBeanDynamic("value11", "value12", "value13"),
                new SomeBeanDynamic("value21", "value22", "value23"));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value1", "value3");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeanDynamicList);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PostMapping("dynamic")
    public String postSomeBeanDynamic(@RequestBody SomeBeanDynamic someBeanDynamic) {
        return someBeanDynamic.toString();
    }

}
