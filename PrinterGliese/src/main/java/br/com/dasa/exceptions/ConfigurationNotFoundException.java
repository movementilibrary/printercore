package br.com.dasa.exceptions;

public class ConfigurationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigurationNotFoundException(String msg, Exception e) {
		super(msg, e); 
	}
}
