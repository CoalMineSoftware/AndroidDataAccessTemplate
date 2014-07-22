package com.coalmine.contentprovider.template.contentvalue.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ContentValue {
	public static final String DEFAULT_NAME = "";

	String name() default DEFAULT_NAME;
}


