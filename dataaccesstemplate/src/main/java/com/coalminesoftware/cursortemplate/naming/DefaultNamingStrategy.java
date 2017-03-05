package com.coalminesoftware.cursortemplate.naming;

/** Simple naming strategy that uses the given name as-is. */
public class DefaultNamingStrategy implements NamingStrategy {
	public String determineName(String fieldName) {
		return fieldName;
	}
}
