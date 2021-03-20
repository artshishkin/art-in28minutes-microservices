package net.shyshkin.study.rest.webservices.restfulwebservices.filtering;

import org.springframework.web.bind.annotation.*;

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
    public String postSomeBeanIgnoreJsonProperty(@RequestBody  SomeBeanIgnoreJsonProperty someBeanIgnoreJsonProperty) {
        return someBeanIgnoreJsonProperty.toString();
    }

}
