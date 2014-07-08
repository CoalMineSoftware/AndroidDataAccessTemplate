package com.coalmine.contentprovider.template.contentvalue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import android.R.bool;
import android.content.ContentValues;

import com.coalmine.contentprovider.template.contentvalue.annotation.ContentValue;

public class AnnotationContentValuesMapper<RowModel> implements ContentValuesMapper<RowModel> {
	Set<Value> modelValues = new HashSet<Value>();

	public AnnotationContentValuesMapper(Class<RowModel> clazz) {
		for(Field field : clazz.getDeclaredFields()) {
			if(field.isAnnotationPresent(ContentValue.class)) {
				ContentValue contentValueAnnotation = field.getAnnotation(ContentValue.class);

				String contentValueName = contentValueAnnotation.name();
				if(ContentValue.DEFAULT_VALUE.equals(contentValueName)) {
					contentValueName = field.getName();
				}

				modelValues.add(new Value(
						contentValueName,
						field,
						ValueType.fromClass(field.getType())));
			}
		}
	}

	public ContentValues mapContentValues(RowModel rowModel) {
		ContentValues contentValues = new ContentValues(modelValues.size());

		try{
			for(Value value : modelValues) {
				switch(value.getType()) {
					case PRIMITIVE_BOOLEAN:
						contentValues.put(value.getValueName(), value.getField().getBoolean(rowModel));
						break;
					case BOOLEAN:
						contentValues.put(value.getValueName(), (Boolean)value.getField().get(rowModel));
						break;
					case PRIMITIVE_BYTE:
						contentValues.put(value.getValueName(), value.getField().getByte(rowModel));
						break;
					case BYTE:
						contentValues.put(value.getValueName(), (Byte)value.getField().get(rowModel));
						break;
					case PRIMITIVE_BYTE_ARRAY:
						contentValues.put(value.getValueName(), (byte[])value.getField().get(rowModel));
						break;
					case PRIMITIVE_FLOAT:
						contentValues.put(value.getValueName(), value.getField().getFloat(rowModel));
						break;
					case FLOAT:
						contentValues.put(value.getValueName(), (Float)value.getField().get(rowModel));
						break;
					case PRIMITIVE_DOUBLE:
						contentValues.put(value.getValueName(), value.getField().getDouble(rowModel));
						break;
					case DOUBLE:
						contentValues.put(value.getValueName(), (Double)value.getField().get(rowModel));
						break;
					case PRIMITIVE_SHORT:
						contentValues.put(value.getValueName(), value.getField().getShort(rowModel));
						break;
					case SHORT:
						contentValues.put(value.getValueName(), (Short)value.getField().get(rowModel));
						break;
					case PRIMITIVE_INTEGER:
						contentValues.put(value.getValueName(), value.getField().getInt(rowModel));
						break;
					case INTEGER:
						contentValues.put(value.getValueName(), (Integer)value.getField().get(rowModel));
						break;
					case PRIMITIVE_LONG:
						contentValues.put(value.getValueName(), value.getField().getLong(rowModel));
						break;
					case LONG:
						contentValues.put(value.getValueName(), (Long)value.getField().get(rowModel));
						break;
					case STRING:
						contentValues.put(value.getValueName(), (String)value.getField().get(rowModel));
						break;
				}
			}
		} catch(Exception e) {
			throw new RuntimeException("", e);
		}

		return contentValues;
	}

	private enum ValueType {
		PRIMITIVE_BOOLEAN,
		BOOLEAN,
		PRIMITIVE_BYTE,
		BYTE,
		PRIMITIVE_BYTE_ARRAY,
		PRIMITIVE_FLOAT,
		FLOAT,
		PRIMITIVE_DOUBLE,
		DOUBLE,
		PRIMITIVE_SHORT,
		SHORT,
		PRIMITIVE_INTEGER,
		INTEGER,
		PRIMITIVE_LONG,
		LONG,
		STRING;

		public static ValueType fromClass(Class<?> clazz) {
			if(Boolean.class.isAssignableFrom(clazz)) {
				return ValueType.BOOLEAN;
			} else if(bool.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_BOOLEAN;
			} else if(Byte.class.isAssignableFrom(clazz)) {
				return ValueType.BYTE;
			} else if(byte.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_BYTE;
			} else if(Byte[].class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_BYTE_ARRAY;
			} else if(Float.class.isAssignableFrom(clazz)) {
				return ValueType.FLOAT;
			} else if(float.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_FLOAT;
			} else if(Double.class.isAssignableFrom(clazz)) {
				return ValueType.DOUBLE;
			} else if(double.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_DOUBLE;
			} else if(Short.class.isAssignableFrom(clazz)) {
				return ValueType.SHORT;
			} else if(short.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_SHORT;
			} else if(Integer.class.isAssignableFrom(clazz)) {
				return ValueType.INTEGER;
			} else if(int.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_INTEGER;
			} else if(Long.class.isAssignableFrom(clazz)) {
				return ValueType.LONG;
			} else if(long.class.isAssignableFrom(clazz)) {
				return ValueType.PRIMITIVE_LONG;
			} else if(String.class.isAssignableFrom(clazz)) {
				return ValueType.STRING;
			}

			throw new IllegalArgumentException("Class must be one of the types allowed by ContentProvider.set().");
		}
	}

	private static class Value {
		private String valueName;
		private Field field;
		private ValueType type;

		public Value(String valueName, Field field, ValueType type) {
			this.valueName = valueName;
			this.field = field;
			this.type = type;
		}

		public String getValueName() {
			return valueName;
		}

		public Field getField() {
			return field;
		}

		public ValueType getType() {
			return type;
		}
	}
}


