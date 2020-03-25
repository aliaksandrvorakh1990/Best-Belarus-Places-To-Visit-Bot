package by.vorakh.alex.best_belarus_places_bot.controller.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class ResourceNotExistException extends RuntimeException {

    private static final long serialVersionUID = 6306256559660545910L;

    public ResourceNotExistException(String message) {
        super(message);
    }

}
