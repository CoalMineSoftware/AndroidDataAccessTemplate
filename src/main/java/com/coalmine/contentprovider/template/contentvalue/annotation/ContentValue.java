package com.coalmine.contentprovider.template.contentvalue.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ContentValue {
	public static final String DEFAULT_VALUE = "";

	String name() default DEFAULT_VALUE;
}


