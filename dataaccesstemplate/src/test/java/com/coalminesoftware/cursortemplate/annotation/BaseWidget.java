package com.coalminesoftware.cursortemplate.annotation;


public class BaseWidget {
	@Column
	private int privateBaseClassField;
	@Column
	protected int protectedBaseClassField;
	@Column
	public int publicBaseClassField;

	@Column(name="renamedPrivateBaseClassField")
	private int namedPrivateBaseClassField;
	@Column(name="renamedProtectedBaseClassField")
	protected int namedProtectedBaseClassField;
	@Column(name="renamedPublicBaseClassField")
	public int namedPublicBaseClassField;

	private int unmappedBaseClassField;


	public int getPrivateBaseClassField() {
		return privateBaseClassField;
	}
	public void setPrivateBaseClassField(int privateBaseClassField) {
		this.privateBaseClassField = privateBaseClassField;
	}

	public int getProtectedBaseClassField() {
		return protectedBaseClassField;
	}
	public void setProtectedBaseClassField(int protectedBaseClassField) {
		this.protectedBaseClassField = protectedBaseClassField;
	}

	public int getPublicBaseClassField() {
		return publicBaseClassField;
	}
	public void setPublicBaseClassField(int publicBaseClassField) {
		this.publicBaseClassField = publicBaseClassField;
	}

	public int getNamedPrivateBaseClassField() {
		return namedPrivateBaseClassField;
	}
	public void setNamedPrivateBaseClassField(int namedPrivateBaseClassField) {
		this.namedPrivateBaseClassField = namedPrivateBaseClassField;
	}

	public int getNamedProtectedBaseClassField() {
		return namedProtectedBaseClassField;
	}
	public void setNamedProtectedBaseClassField(int namedProtectedBaseClassField) {
		this.namedProtectedBaseClassField = namedProtectedBaseClassField;
	}

	public int getNamedPublicBaseClassField() {
		return namedPublicBaseClassField;
	}
	public void setNamedPublicBaseClassField(int namedPublicBaseClassField) {
		this.namedPublicBaseClassField = namedPublicBaseClassField;
	}

	public int getUnmappedBaseClassField() {
		return unmappedBaseClassField;
	}
	public void setUnmappedBaseClassField(int unmappedBaseClassField) {
		this.unmappedBaseClassField = unmappedBaseClassField;
	}
}


