package br.com.dasa.enums;

public enum LogEnum {
	
	INFO("#1a1aff"), WARN("#e6e600"), ERROR("#cc0000");

	private String color; 
	
	
	LogEnum(String color) {
		this.color = color; 
	}
	
	
	public String getColor() {
		return this.color; 
	}
}
