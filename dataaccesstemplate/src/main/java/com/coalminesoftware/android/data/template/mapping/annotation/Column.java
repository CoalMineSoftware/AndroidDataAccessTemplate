package com.coalminesoftware.android.data.template.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Marks a field whose value should be stored in {@link ContentValues} when the enclosing class is
 * used with an {@link AnnotationContentValuesMapper}, or populated from a {@link Cursor} when the
 * enclosing class is used with an {@link AnnotationRowMapper}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	public static final String DEFAULT_NAME = "";

	/**
	 * The name under which the field's value should be stored in a ContentValues object, or from
	 * which Cursor column its values should be populated from.
	 */
	String name() default DEFAULT_NAME;
}
