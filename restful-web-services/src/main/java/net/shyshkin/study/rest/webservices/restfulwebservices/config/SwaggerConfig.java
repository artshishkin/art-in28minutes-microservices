package net.shyshkin.study.rest.webservices.restfulwebservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact CONTACT = new Contact("Artem Shyshkin", "http://shyshkin.net", "d.art.shyshkin@gmail.com");
    public static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = Set.of(APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE);

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${application.description}")
    private String applicationDescription;

    @Bean
    public Docket api() {
        ApiInfo API_INFO = new ApiInfo(
                "Photo App API",
                applicationDescription,
                applicationVersion,
                "urn:tos",
                CONTACT,
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0.txt",
                Collections.emptyList());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(API_INFO)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
