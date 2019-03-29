package br.com.dasa.exceptions;

public class SOException extends RuntimeException {

	public SOException(Exception e, String msg) {
		super(msg, e);
	}

	private static final long serialVersionUID = 1L;

}
