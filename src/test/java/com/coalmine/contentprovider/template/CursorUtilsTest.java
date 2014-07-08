package com.coalmine.contentprovider.template;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.MatrixCursor;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CursorUtilsTest {
	private static final String COLUMN_NAME = "column";
	private static final int COLUMN_INDEX = 0;
	private static final String INVALID_COLUMN_NAME = "invalid";
	private static final int INVALID_COLUMN_INDEX = 1;

	// Values returned by MatrixCursor when the value of null column is gotten
	private static final int UNDEFINED_INTEGER_VALUE = 0;
	private static final long UNDEFINED_LONG_VALUE = 0;
	private static final short UNDEFINED_SHORT_VALUE = 0;
	private static final float UNDEFINED_FLOAT_VALUE = 0;
	private static final double UNDEFINED_DOUBLE_VALUE = 0;

	private static <T> T[] asArray(T... objects) {
		return objects;
	}

	/** Similar to utility methods that build a "singleton" Collection containing a single value,
	 * this method builds a Cursor with a single row and column, containing the given value. */
	private static Cursor buildSingletonCursor(Object value) {
		MatrixCursor cursor = new MatrixCursor(asArray(COLUMN_NAME));
		cursor.addRow(asArray(value));
		cursor.moveToFirst();

		return cursor;
	}


	@Test
	public void testGetRequiredIntegerByName() {
		int columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertEquals(columnValue, CursorUtils.getRequiredInteger(cursor,COLUMN_NAME));
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

		assertEquals(UNDEFINED_INTEGER_VALUE, CursorUtils.getRequiredInteger(cursor, COLUMN_NAME));
	}

	@Test
	public void testGetIntegerByName() {
		Integer value = 5;
		Cursor cursor = buildSingletonCursor(value);

		assertEquals(value, CursorUtils.getInteger(cursor,COLUMN_NAME));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetIntegerByName_withInvalidColumnName() {
		Integer value = 5;
		Cursor cursor = buildSingletonCursor(value);

		CursorUtils.getInteger(cursor,INVALID_COLUMN_NAME);
	}

	@Test
	public void testGetIntegerByName_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertNull(CursorUtils.getInteger(cursor,COLUMN_NAME));
	}

	@Test
	public void testGetIntegerByIndex() {
		Integer value = 5;
		Cursor cursor = buildSingletonCursor(value);

		assertEquals(value, CursorUtils.getInteger(cursor, COLUMN_INDEX));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetIntegerByIndex_withInvalidColumnName() {
		Integer value = 5;
		Cursor cursor = buildSingletonCursor(value);

		CursorUtils.getInteger(cursor,INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetIntegerByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertNull(CursorUtils.getInteger(cursor, COLUMN_INDEX));
	}

	@Test
	public void testGetRequiredLongByName() {
		long columnValue = 5;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertEquals(columnValue, CursorUtils.getRequiredLong(cursor, COLUMN_NAME));
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

		assertEquals(UNDEFINED_LONG_VALUE, CursorUtils.getRequiredLong(cursor, COLUMN_NAME));
	}

	@Test
	public void testGetLongByName() {
		Long columnValue = 5L;
		Cursor cursor = buildSingletonCursor(columnValue);

		assertEquals(columnValue, CursorUtils.getLong(cursor, COLUMN_NAME));
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

		assertNull(CursorUtils.getLong(cursor, COLUMN_NAME));
	}

	@Test
	public void testGetLongByIndex() {
		Long value = 5L;
		Cursor cursor = buildSingletonCursor(value);

		assertEquals(value, CursorUtils.getLong(cursor, COLUMN_INDEX));
	}

	@Test(expected=CursorIndexOutOfBoundsException.class)
	public void testGetLongByIndex_withInvalidColumnName() {
		Long value = 5L;
		Cursor cursor = buildSingletonCursor(value);

		CursorUtils.getLong(cursor, INVALID_COLUMN_INDEX);
	}

	@Test
	public void testGetLongByIndex_withNullColumnValue() {
		Cursor cursor = buildSingletonCursor(null);

		assertNull(CursorUtils.getLong(cursor, COLUMN_INDEX));
	}

	@Test
	public void testGetRequiredFloatByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloatByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloatByIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequiredDoubleByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDoubleByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDoubleByIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequiredBooleanByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequiredBooleanByIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBooleanByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBooleanByIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequiredStringByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStringByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStringByIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRequiredBlobByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBlobByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBlobCursorByIndex() {
		fail("Not yet implemented");
	}
}


