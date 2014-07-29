package com.coalmine.contentprovider.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.content.ContentValues;

/** Marks a field whose value should be stored in {@link ContentValues} when
 * the enclosing class is used with an {@link AnnotationContentValuesMapper}. */
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentValue {
	public static final String DEFAULT_NAME = "";

	String name() default DEFAULT_NAME;
}


