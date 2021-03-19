package net.shyshkin.study.rest.webservices.restfulwebservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Integer id;

    @Size(min = 2, max = 255, message = "Name must have from 2 to 255 characters")
    private String name;

    @Past
    private LocalDate birthDate;

    @Builder.Default
    private List<Post> posts = new ArrayList<>();

}
