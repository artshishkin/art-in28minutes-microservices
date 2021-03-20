package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("filtering")
public class FilteringController {

    @GetMapping("personal")
    public SomeBean getSomeBean() {
        return new SomeBean("value1", "value2", "value3");
    }

    @GetMapping("global")
    public SomeBeanIgnoreJsonProperty getSomeBeanIgnoreJsonProperty() {
        return new SomeBeanIgnoreJsonProperty("value1", "value2", "value3");
    }

}
