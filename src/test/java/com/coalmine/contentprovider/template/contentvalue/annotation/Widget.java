package com.coalmine.contentprovider.template.contentvalue.annotation;

import com.coalmine.contentprovider.template.contentvalue.annotation.ContentValue;

public class Widget extends BaseWidget {
	@ContentValue
	private int privateField;
	@ContentValue
	protected int protectedField;
	@ContentValue
	public int publicField;

	@ContentValue(name="renamedPrivateField")
	private int namedPrivateField;
	@ContentValue(name="renamedProtectedField")
	protected int namedProtectedField;
	@ContentValue(name="renamedPublicField")
	public int namedPublicField;

	private int unmappedField;


	public int getPrivateField() {
		return privateField;
	}
	public void setPrivateField(int privateField) {
		this.privateField = privateField;
	}

	public int getProtectedField() {
		return protectedField;
	}
	public void setProtectedField(int protectedField) {
		this.protectedField = protectedField;
	}

	public int getPublicField() {
		return publicField;
	}
	public void setPublicField(int publicField) {
		this.publicField = publicField;
	}

	public int getNamedPrivateField() {
		return namedPrivateField;
	}
	public void setNamedPrivateField(int namedPrivateField) {
		this.namedPrivateField = namedPrivateField;
	}

	public int getNamedProtectedField() {
		return namedProtectedField;
	}
	public void setNamedProtectedField(int namedProtectedField) {
		this.namedProtectedField = namedProtectedField;
	}

	public int getNamedPublicField() {
		return namedPublicField;
	}
	public void setNamedPublicField(int namedPublicField) {
		this.namedPublicField = namedPublicField;
	}

	public int getUnmappedField() {
		return unmappedField;
	}
	public void setUnmappedField(int unmappedField) {
		this.unmappedField = unmappedField;
	}
}


