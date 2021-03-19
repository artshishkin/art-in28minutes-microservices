package net.shyshkin.study.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    private Integer id;
    private String title;
    private String content;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

}
