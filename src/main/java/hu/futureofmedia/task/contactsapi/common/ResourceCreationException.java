package hu.futureofmedia.task.contactsapi.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceCreationException extends RuntimeException {
    public ResourceCreationException(String resource) {
        super(String.format("Couldn't create %s resource", resource));
    }
}
