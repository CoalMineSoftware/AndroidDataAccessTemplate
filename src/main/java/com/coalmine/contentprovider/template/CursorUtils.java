package com.coalmine.contentprovider.template;

import android.database.Cursor;


/** Convenience methods for retrieving values from a {@link Cursor} by column name and/or accounting for null values. */
public abstract class CursorUtils {
	public static byte getRequiredByte(final Cursor cursor, final String columnName) {
		return getRequiredByte(cursor, cursor.getColumnIndex(columnName));
	}

	public static byte getRequiredByte(final Cursor cursor, final int columnIndex) {
		return (byte)cursor.getShort(columnIndex);
	}

	public static Byte getByte(final Cursor cursor, final String columnName) {
		return getByte(cursor, cursor.getColumnIndex(columnName));
	}

	public static Byte getByte(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : getRequiredByte(cursor, columnIndex);
	}


	public static short getRequiredShort(final Cursor cursor, final String columnName) {
		return cursor.getShort(cursor.getColumnIndex(columnName));
	}

	public static Short getShort(final Cursor cursor, final String columnName) {
		return getShort(cursor, cursor.getColumnIndex(columnName));
	}

	public static Short getShort(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getShort(columnIndex);
	}


	public static int getRequiredInteger(final Cursor cursor, final String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static Integer getInteger(final Cursor cursor, final String columnName) {
		return getInteger(cursor, cursor.getColumnIndex(columnName));
	}

	public static Integer getInteger(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getInt(columnIndex);
	}


	public static long getRequiredLong(final Cursor cursor, final String columnName) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}

	public static Long getLong(final Cursor cursor, final String columnName) {
		return getLong(cursor, cursor.getColumnIndex(columnName));
	}

	public static Long getLong(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getLong(columnIndex);
	}


	public static float getRequiredFloat(final Cursor cursor, final String columnName) {
		return cursor.getFloat(cursor.getColumnIndex(columnName));
	}

	public static Float getFloat(final Cursor cursor, final String columnName) {
		return getFloat(cursor, cursor.getColumnIndex(columnName));
	}

	public static Float getFloat(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getFloat(columnIndex);
	}


	public static double getRequiredDouble(final Cursor cursor, final String columnName) {
		return cursor.getDouble(cursor.getColumnIndex(columnName));
	}

	public static Double getDouble(final Cursor cursor, final String columnName) {
		return getDouble(cursor, cursor.getColumnIndex(columnName));
	}

	public static Double getDouble(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getDouble(columnIndex);
	}


	public static boolean getRequiredBoolean(final Cursor cursor, final String columnName) {
		return getRequiredBoolean(cursor, cursor.getColumnIndex(columnName));
	}

	public static boolean getRequiredBoolean(final Cursor cursor, final int columnIndex) {
		return cursor.getInt(columnIndex) == 1;
	}

	public static Boolean getBoolean(final Cursor cursor, final String columnName) {
		return getBoolean(cursor, cursor.getColumnIndex(columnName));
	}

	public static Boolean getBoolean(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getInt(columnIndex) == 1;
	}


	public static String getRequiredString(final Cursor cursor, final String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public static String getString(final Cursor cursor, final String columnName) {
		return getString(cursor, cursor.getColumnIndex(columnName));
	}

	public static String getString(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getString(columnIndex);
	}


	public static byte[] getRequiredBlob(final Cursor cursor, final String columnName) {
		return cursor.getBlob(cursor.getColumnIndex(columnName));
	}

	public static byte[] getBlob(final Cursor cursor, final String columnName) {
		return getBlob(cursor, cursor.getColumnIndex(columnName));
	}

	public static byte[] getBlob(final Cursor cursor, final int columnIndex) {
		return cursor.isNull(columnIndex)? null : cursor.getBlob(columnIndex);
	}
}


