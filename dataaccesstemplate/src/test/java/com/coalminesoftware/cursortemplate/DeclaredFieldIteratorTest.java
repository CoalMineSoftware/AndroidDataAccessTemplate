package com.coalminesoftware.cursortemplate;

import com.coalminesoftware.cursortemplate.annotation.Widget;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DeclaredFieldIteratorTest {
	/** All of the columns that are known to exist in Widget and its descendants (excluding Object.) */
	private static final Set<String> WIDGET_FIELD_NAMES = new HashSet<>();

	private DeclaredFieldIterator fieldIterator;

	static {
		WIDGET_FIELD_NAMES.add("privateField");
		WIDGET_FIELD_NAMES.add("protectedField");
		WIDGET_FIELD_NAMES.add("publicField");
		WIDGET_FIELD_NAMES.add("namedPrivateField");
		WIDGET_FIELD_NAMES.add("namedProtectedField");
		WIDGET_FIELD_NAMES.add("namedPublicField");
		WIDGET_FIELD_NAMES.add("unmappedField");
		WIDGET_FIELD_NAMES.add("privateBaseClassField");
		WIDGET_FIELD_NAMES.add("protectedBaseClassField");
		WIDGET_FIELD_NAMES.add("publicBaseClassField");
		WIDGET_FIELD_NAMES.add("namedPrivateBaseClassField");
		WIDGET_FIELD_NAMES.add("namedProtectedBaseClassField");
		WIDGET_FIELD_NAMES.add("namedPublicBaseClassField");
		WIDGET_FIELD_NAMES.add("unmappedBaseClassField");
	}

	@Before
	public void setup() {
		fieldIterator = new DeclaredFieldIterator(Widget.class);
	}

	@Test
	public void testConstructor() {
		assertEquals(Widget.class, fieldIterator.currentClass);
		assertArrayEquals(Widget.class.getDeclaredFields(), fieldIterator.currentClassFields);
		assertEquals(1, fieldIterator.currentFieldIndex);
		assertNotNull(fieldIterator.nextField);
	}

	@Test
	public void testHasNext() {
		for(int i=1; i<=WIDGET_FIELD_NAMES.size(); i++) {
			assertTrue("Method hasNext() returned false unexpectedly, on call "+i,
					fieldIterator.hasNext());

			fieldIterator.advanceToNextField();
		}

		assertFalse("Iterator returned true when no more fields should exist to iterate over",
				fieldIterator.hasNext());
	}

	@Test
	public void testNext() {
		while(fieldIterator.hasNext()) {
			Set<String> iteratedFieldNames = new HashSet<String>();
			for(int i=0; i<WIDGET_FIELD_NAMES.size(); i++) { // Iterate over the known number of fields using only next() and not hasNext()
				iteratedFieldNames.add(fieldIterator.next().getName());
			}

			assertEquals(WIDGET_FIELD_NAMES, iteratedFieldNames);
		}
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		fieldIterator.remove();
	}
}
