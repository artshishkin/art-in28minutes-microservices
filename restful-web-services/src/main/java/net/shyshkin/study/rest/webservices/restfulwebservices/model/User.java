package net.shyshkin.study.rest.webservices.restfulwebservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Integer id;
    private String name;
    private LocalDate birthDate;

    @Builder.Default
    private List<Post> posts = new ArrayList<>();

}
