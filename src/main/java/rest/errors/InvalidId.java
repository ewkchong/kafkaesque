package rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.SEE_OTHER)
public class InvalidId extends RuntimeException{

    public InvalidId(String s) {
        super("InvalidId");
    }
}

