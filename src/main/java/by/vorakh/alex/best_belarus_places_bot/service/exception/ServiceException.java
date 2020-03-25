package by.vorakh.alex.best_belarus_places_bot.service.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -1052206934491683639L;

    public ServiceException(String message, Throwable ex) {
        super(message, ex);
    }

    public ServiceException(String message) {
        super(message);
    }

}
