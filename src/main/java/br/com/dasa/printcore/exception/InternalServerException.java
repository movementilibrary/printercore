package br.com.dasa.printcore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InternalServerException(String message, Exception e) {
        super(message, e);
    }

    public InternalServerException (String message) {
        super(message);
    }
    
    
}
