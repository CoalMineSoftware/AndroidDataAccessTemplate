package com.coalmine.contentprovider.template;

import android.database.Cursor;

public abstract class CursorUtils {
	public static int getInteger(final Cursor cursor, final String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static Integer getNullableInteger(final Cursor cursor, final String columnName) {
		return getNullableInteger(cursor, cursor.getColumnIndex(columnName));
	}

	public static Integer getNullableInteger(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getInt(columnIndex);
	}

	
	public static long getLong(final Cursor cursor, final String columnName) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}

	public static Long getNullableLong(final Cursor cursor, final String columnName) {
		return getNullableLong(cursor, cursor.getColumnIndex(columnName));
	}

	public static Long getNullableLong(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getLong(columnIndex);
	}


	public static float getFloat(final Cursor cursor, final String columnName) {
		return cursor.getFloat(cursor.getColumnIndex(columnName));
	}

	public static Float getNullableFloat(final Cursor cursor, final String columnName) {
		return getNullableFloat(cursor, cursor.getColumnIndex(columnName));
	}

	public static Float getNullableFloat(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getFloat(columnIndex);
	}


	public static double getDouble(final Cursor cursor, final String columnName) {
		return cursor.getDouble(cursor.getColumnIndex(columnName));
	}

	public static Double getNullableDouble(final Cursor cursor, final String columnName) {
		return getNullableDouble(cursor, cursor.getColumnIndex(columnName));
	}

	public static Double getNullableDouble(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getDouble(columnIndex);
	}


	public static boolean getBoolean(final Cursor cursor, final String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName)) == 1;
	}

	public static Boolean getNullableBoolean(final Cursor cursor, final String columnName) {
		return getNullableBoolean(cursor, cursor.getColumnIndex(columnName));
	}

	public static Boolean getNullableBoolean(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getInt(columnIndex) == 1;
	}


	public static String getString(final Cursor cursor, final String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public static String getNullableString(final Cursor cursor, final String columnName) {
		return getNullableString(cursor, cursor.getColumnIndex(columnName));
	}

	public static String getNullableString(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getString(columnIndex);
	}
}


