package net.shyshkin.study.rest.webservices.restfulwebservices.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(String message) {
        super(message);
    }
}
