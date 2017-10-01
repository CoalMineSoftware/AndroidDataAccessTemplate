package com.coalminesoftware.android.data.template.mapping.annotation;

public class Widget extends BaseWidget {
	@Column
	private int privateField;
	@Column
	protected int protectedField;
	@Column
	public int publicField;

	@Column(name="renamedPrivateField")
	private int namedPrivateField;
	@Column(name="renamedProtectedField")
	protected int namedProtectedField;
	@Column(name="renamedPublicField")
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
