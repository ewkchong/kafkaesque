package rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.SEE_OTHER)
public class WrongServer extends RuntimeException{

    public WrongServer(String s) {
        super("Check here "+s);
    }
}
