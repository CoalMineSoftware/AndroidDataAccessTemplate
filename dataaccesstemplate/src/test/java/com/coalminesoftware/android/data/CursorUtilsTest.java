package com.coalminesoftware.android.data;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.MatrixCursor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CursorUtilsTest {
	private static final String COLUMN_NAME = "column";
	private static final int COLUMN_INDEX = 0;
	private static final String INVALID_COLUMN_NAME = "invalid";
	private static final int INVALID_COLUMN_INDEX = 1;

	// Values returned by MatrixCursor when the value of null column is gotten
	private static final byte UNDEFINED_BYTE_VALUE = 0;
	private static final short UNDEFINED_SHORT_VALUE = 0;
	private static final int UNDEFINED_INTEGER_VALUE = 0;
	private static final long UNDEFINED_LONG_VALUE = 0;
	private static final float UNDEFINED_FLOAT_VALUE = 0;
	private static final double UNDEFINED_DOUBLE_VALUE = 0;


	// Byte extraction tests

	@Test
	public void testGetRequiredByteByName() {
		byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredByte(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredByteByName_withInvalidColumnName() {
		byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredByte(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredByteByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredByte(cursor, COLUMN_NAME), is(UNDEFINED_BYTE_VALUE));
	}

	@Test
	public void testGetRequiredByteByIndex() {
		byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredByte(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredByteByIndex_withInvalidColumnIndex() {
		byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getByte(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetRequiredByteByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getByte(cursor, COLUMN_INDEX), nullValue());
	}

	@Test
	public void testGetByteByName() {
		Byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getByte(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetByteByName_withInvalidColumnName() {
		Byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getByte(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetByteByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getByte(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetByteByIndex() {
		Byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getByte(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetByteByIndex_withInvalidColumnIndex() {
		Byte columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getByte(cursor,INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetByteByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getByte(cursor, COLUMN_INDEX), nullValue());
	}


	// Short extraction tests

	@Test
	public void testGetRequiredShortByName() {
		short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredShort(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredShortByName_withInvalidColumnName() {
		short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredShort(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredShortByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredLong(cursor, COLUMN_NAME), is(UNDEFINED_LONG_VALUE));
	}

	@Test
	public void testGetShortByName() {
		Short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getShort(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetShortByName_withInvalidColumnName() {
		Short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getShort(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetShortByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getShort(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetShortByIndex() {
		Short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getShort(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetShortByIndex_withInvalidColumnIndex() {
		Short columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getShort(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetShortByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getShort(cursor, COLUMN_INDEX), nullValue());
	}


	// Integer extraction tests

	@Test
	public void testGetRequiredIntegerByName() {
		int columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredInteger(cursor,COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredIntegerByName_withInvalidColumnName() {
		int columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredInteger(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredIntegerByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredInteger(cursor, COLUMN_NAME), is(UNDEFINED_INTEGER_VALUE));
	}

	@Test
	public void testGetIntegerByName() {
		Integer columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getInteger(cursor,COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetIntegerByName_withInvalidColumnName() {
		Integer columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getInteger(cursor,INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetIntegerByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getInteger(cursor,COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetIntegerByIndex() {
		Integer columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getInteger(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetIntegerByIndex_withInvalidColumnIndex() {
		Integer columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getInteger(cursor,INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetIntegerByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getInteger(cursor, COLUMN_INDEX), nullValue());
	}


	// Long extraction tests

	@Test
	public void testGetRequiredLongByName() {
		long columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredLong(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredLongByName_withInvalidColumnName() {
		long columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredLong(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredLongByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredLong(cursor, COLUMN_NAME), is(UNDEFINED_LONG_VALUE));
	}

	@Test
	public void testGetLongByName() {
		Long columnValue = 5L;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getLong(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetLongByName_withInvalidColumnName() {
		Long columnValue = 5L;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getLong(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetLongByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getLong(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetLongByIndex() {
		Long columnValue = 5L;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getLong(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetLongByIndex_withInvalidColumnIndex() {
		Long columnValue = 5L;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getLong(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetLongByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getLong(cursor, COLUMN_INDEX), nullValue());
	}

	// Float extraction tests

	@Test
	public void testGetRequiredFloatByName() {
		float columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredFloat(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredFloatByName_withInvalidColumnName() {
		float columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredFloat(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredFloatByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredFloat(cursor, COLUMN_NAME), is(UNDEFINED_FLOAT_VALUE));
	}

	@Test
	public void testGetFloatByName() {
		Float columnValue = 5f;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getFloat(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetFloatByName_withInvalidColumnName() {
		Float columnValue = 5f;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getFloat(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetFloatByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getFloat(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetFloatByIndex() {
		Float columnValue = 5f;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getFloat(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetFloatByIndex_withInvalidColumnIndex() {
		Float columnValue = 5f;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getFloat(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetFloatByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getFloat(cursor, COLUMN_INDEX), nullValue());
	}

	// Double extraction tests

	@Test
	public void testGetRequiredDoubleByName() {
		double columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredDouble(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredDoubleByName_withInvalidColumnName() {
		double columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredDouble(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredDoubleByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredDouble(cursor, COLUMN_NAME), is(UNDEFINED_DOUBLE_VALUE));
	}

	@Test
	public void testGetDoubleByName() {
		Double columnValue = 5d;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getDouble(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetDoubleByName_withInvalidColumnName() {
		Double columnValue = 5d;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getDouble(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetDoubleByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getDouble(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetDoubleByIndex() {
		Double columnValue = 5d;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getDouble(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetDoubleByIndex_withInvalidColumnIndex() {
		Double columnValue = 5d;
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getDouble(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetDoubleByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getDouble(cursor, COLUMN_INDEX), nullValue());
	}

	// Boolean extraction tests
	
	@Test
	public void testGetRequiredBooleanByName() {
		int columnValue = 1;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredBoolean(cursor, COLUMN_NAME), is(true));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredBooleanByName_withInvalidColumnName() {
		Cursor cursor = buildSingletonCursor(true);

		CursorUtils.getRequiredBoolean(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredBooleanByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredBoolean(cursor, COLUMN_NAME), is(false));
	}

	@Test
	public void testGetBooleanByName() {
		int columnValue = 1;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getBoolean(cursor, COLUMN_NAME), is(true));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetBooleanByName_withInvalidColumnName() {
		Cursor cursor = buildSingletonCursor(true);

		CursorUtils.getBoolean(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetBooleanByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getBoolean(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetBooleanByIndex() {
		int columnValue = 1;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getBoolean(cursor, COLUMN_INDEX), is(true));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetBooleanByIndex_withInvalidColumnIndex() {
		Cursor cursor = buildSingletonCursor(true);

		CursorUtils.getBoolean(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetBooleanByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getBoolean(cursor, COLUMN_INDEX), nullValue());
	}

	// String extraction tests

	@Test
	public void testGetRequiredStringByName() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredString(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetRequiredStringByName_withInvalidColumnName() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredString(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredStringByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredString(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetStringByName() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getString(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetStringByName_withInvalidColumnName() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getString(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetStringByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getString(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	public void testGetStringByIndex() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getString(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetStringByIndex_withInvalidColumnIndex() {
		String columnValue = "test";
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getString(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetStringByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getString(cursor, COLUMN_INDEX), nullValue());
	}

	// Blob extraction tests

	@Test
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetRequiredBlobByName() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getRequiredBlob(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetRequiredBlobByName_withInvalidColumnName() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getRequiredString(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetRequiredBlobByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getRequiredBlob(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetBlobByName() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getBlob(cursor, COLUMN_NAME), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetBlobByName_withInvalidColumnName() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getBlob(cursor, INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetBlobByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getBlob(cursor, COLUMN_NAME), nullValue());
	}

	@Test
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetBlobByIndex() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		assertThat(CursorUtils.getBlob(cursor, COLUMN_INDEX), is(columnValue));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	@SuppressLint("NewApi") // Local JUnit shouldn't be affected by platform levels
	public void testGetBlobByIndex_withInvalidColumnIndex() {
		byte[] columnValue = "test".getBytes(StandardCharsets.UTF_8);
		Cursor cursor = buildSingletonCursor(columnValue);

		CursorUtils.getBlob(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetBlobByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertThat(CursorUtils.getBlob(cursor, COLUMN_INDEX), nullValue());
	}


	/** Similar to utility methods that build a "singleton" Collection - a collection containing a single
	 * value - this method builds a Cursor with a single row and column, containing the given value. */
	private static Cursor buildSingletonCursor(Object value) {
		MatrixCursor cursor = new MatrixCursor(asArray(COLUMN_NAME));
		cursor.addRow(asArray(value));
		cursor.moveToFirst();

		return cursor;
	}

	private static <T> T[] asArray(T... objects) {
		return objects;
	}
}
