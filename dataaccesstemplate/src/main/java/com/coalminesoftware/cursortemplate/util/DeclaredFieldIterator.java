package com.coalminesoftware.cursortemplate.util;

import java.lang.reflect.Field;
import java.util.Iterator;

/** Iterator that iterates over all of the given Class's declared fields, including inherited ones. */
public class DeclaredFieldIterator implements Iterator<Field> {
	protected Field nextField;

	protected Class<?> mCurrentClass;
	protected Field[] mCurrentClassFields;
	protected int mCurrentFieldIndex;

	public DeclaredFieldIterator(Class<?> currentClass) {
		if(currentClass == null) {
			throw new IllegalArgumentException("A class is required to iterate over");
		}

		mCurrentClass = currentClass;
		mCurrentClassFields = currentClass.getDeclaredFields();

		advanceToNextField();
	}

	@Override
	public boolean hasNext() {
		return nextField != null;
	}

	@Override
	public Field next() {
		Field currentNext = nextField;
		advanceToNextField();

		return currentNext;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot delete Fields.");
	}

	protected void advanceToNextField() {
		while(!Object.class.equals(mCurrentClass)) {
			if(mCurrentFieldIndex < mCurrentClassFields.length) {
				nextField = mCurrentClassFields[mCurrentFieldIndex++];
				return;
			}

			advanceToNextAncestor();
		}

		nextField = null;
	}

	protected void advanceToNextAncestor() {
		mCurrentClass = mCurrentClass.getSuperclass();
		mCurrentClassFields = mCurrentClass.getDeclaredFields();
		mCurrentFieldIndex = 0;
	}
}
