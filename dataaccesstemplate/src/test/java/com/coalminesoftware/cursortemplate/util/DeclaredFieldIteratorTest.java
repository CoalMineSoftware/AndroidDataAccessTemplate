package com.coalminesoftware.cursortemplate.util;

import com.coalminesoftware.cursortemplate.annotation.Widget;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DeclaredFieldIteratorTest {
	/** All of the columns that are known to exist in Widget and its ancestors (excluding Object.) */
	private static final Set<String> WIDGET_FIELD_NAMES = buildWidgetFieldNameSet();

	private DeclaredFieldIterator fieldIterator;

	@Before
	public void setup() {
		fieldIterator = new DeclaredFieldIterator(Widget.class);
	}

	@Test
	public void testConstructor() {
		assertEquals(Widget.class, fieldIterator.mCurrentClass);
		assertArrayEquals(Widget.class.getDeclaredFields(), fieldIterator.mCurrentClassFields);
		assertEquals(1, fieldIterator.mCurrentFieldIndex);
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

	private static Set<String> buildWidgetFieldNameSet() {
		Set<String> names = new HashSet<>();

		names.add("privateField");
		names.add("protectedField");
		names.add("publicField");
		names.add("namedPrivateField");
		names.add("namedProtectedField");
		names.add("namedPublicField");
		names.add("unmappedField");
		names.add("privateBaseClassField");
		names.add("protectedBaseClassField");
		names.add("publicBaseClassField");
		names.add("namedPrivateBaseClassField");
		names.add("namedProtectedBaseClassField");
		names.add("namedPublicBaseClassField");
		names.add("unmappedBaseClassField");

		return Collections.unmodifiableSet(names);
	}
}
