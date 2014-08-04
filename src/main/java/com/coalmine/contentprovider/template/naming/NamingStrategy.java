package com.coalmine.contentprovider.template.naming;

/** Strategy that, given a Java identifier (e.g., a class or field name,) determines a corresponding
 * name for use as an identifier elsewhere, like with ContentProviders and databases. */
public interface NamingStrategy {
	String determineName(String fieldName);
}


