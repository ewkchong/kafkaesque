package rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.SEE_OTHER)
public class InvalidCity extends RuntimeException{

    public InvalidCity(String s) {
        super("Invalid city");
    }
}