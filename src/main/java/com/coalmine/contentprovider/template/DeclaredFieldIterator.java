package com.coalmine.contentprovider.template;

import java.lang.reflect.Field;
import java.util.Iterator;

/** Iterator that iterates over all of the given Class's declared Fields, including inherited ones. */
public class DeclaredFieldIterator implements Iterator<Field> {
	protected Field nextField;

	protected Class<?> currentClass;
	protected Field[] currentClassFields;
	protected int currentFieldIndex;

	public DeclaredFieldIterator(Class<?> currentClass) {
		if(currentClass == null) {
			throw new IllegalArgumentException("A class is required to iterate over");
		}

		this.currentClass = currentClass;
		currentClassFields = currentClass.getDeclaredFields();

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
		while(!Object.class.equals(currentClass)) {
			if(currentFieldIndex < currentClassFields.length) {
				nextField = currentClassFields[currentFieldIndex++];
				return;
			}

			advanceToNextAncestor();
		}

		nextField = null;
	}

	protected void advanceToNextAncestor() {
		currentClass = currentClass.getSuperclass();
		currentClassFields = currentClass.getDeclaredFields();
		currentFieldIndex = 0;
	}
}


