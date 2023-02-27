package trask.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AllErrors extends RuntimeException {

    public AllErrors(String message) {
        super(message);
    }
}