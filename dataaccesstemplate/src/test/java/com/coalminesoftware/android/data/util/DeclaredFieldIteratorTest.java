package com.coalminesoftware.android.data.util;

import com.coalminesoftware.android.data.annotation.Widget;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeclaredFieldIteratorTest {
	/** All of the columns that are known to exist in Widget and its ancestors (excluding Object.) */
	private static final Set<String> WIDGET_FIELD_NAMES = buildWidgetFieldNameSet();

	private DeclaredFieldIterator mFieldIterator;

	@Before
	public void setup() {
		mFieldIterator = new DeclaredFieldIterator(Widget.class);
	}

	@Test
	public void testConstructor() {
		// Because is() is overridden so users can easily assert that an object is of a certain
		// type, it doesn't seem to be possible to test for equality when the object being tested is
		// a Class object. So instead, call equals() directly and check that it returned true.
		assertThat(Widget.class.equals(mFieldIterator.mCurrentClass), is(true));
		assertThat(mFieldIterator.mCurrentClassFields, is(Widget.class.getDeclaredFields()));
		assertThat(mFieldIterator.mCurrentFieldIndex, is(1));
		assertThat(mFieldIterator.nextField, notNullValue());
	}

	@Test
	public void testHasNext() {
		for(int i=1; i<=WIDGET_FIELD_NAMES.size(); i++) {
			assertThat(mFieldIterator.hasNext(), is(true));

			mFieldIterator.advanceToNextField();
		}

		assertThat(mFieldIterator.hasNext(), is(false));
	}

	@Test
	public void testNext() {
		while(mFieldIterator.hasNext()) {
			Set<String> iteratedFieldNames = new HashSet<String>();
			for(int i=0; i<WIDGET_FIELD_NAMES.size(); i++) { // Iterate over the known number of fields using only next() and not hasNext()
				iteratedFieldNames.add(mFieldIterator.next().getName());
			}

			assertThat(iteratedFieldNames, is(WIDGET_FIELD_NAMES));
		}
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		mFieldIterator.remove();
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
