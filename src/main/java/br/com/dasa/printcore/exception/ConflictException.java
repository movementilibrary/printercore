package br.com.dasa.printcore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ConflictException() {
        super("O recurso requisitado conflitou com o recurso existente");
    }

    public ConflictException(String message) {
        super(message);
    }
}