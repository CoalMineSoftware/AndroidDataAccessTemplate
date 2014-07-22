package com.coalmine.contentprovider.template.contentvalue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import android.R.bool;
import android.content.ContentValues;

import com.coalmine.contentprovider.template.contentvalue.annotation.ContentValue;

/** A {@link ContentValuesMapper} implementation that maps all of the model object's fields (including inherited
 * fields) that are annotated with {@link ContentValue}.  The field's {@link ContentValue#name()} is used as the key of
 * the value inserted into the generated ContentValues.  If a name is not provided, the field's name is used instead. */
public class AnnotatedContentValuesMapper<RowModel> implements ContentValuesMapper<RowModel> { 
	Set<MappableField> mappableFields = new HashSet<MappableField>();

	public AnnotatedContentValuesMapper(Class<RowModel> modelClass) {
		for(Class<?> currentClass=modelClass; !Object.class.equals(currentClass); currentClass=currentClass.getSuperclass()) {
			for(Field field : modelClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(ContentValue.class)) {
					if(!field.isAccessible()) {
						field.setAccessible(true);
					}

					ContentValue contentValueAnnotation = field.getAnnotation(ContentValue.class);

					String valueKey = contentValueAnnotation.name();
					if(ContentValue.DEFAULT_NAME.equals(valueKey)) {
						valueKey = field.getName();
					}

					mappableFields.add(new MappableField(valueKey, field,
							determineFieldMappingStrategyForClass(field.getType())));
				}
			}
		}
	}

	@Override
	public ContentValues mapContentValues(RowModel rowModel) {
		ContentValues contentValues = new ContentValues(mappableFields.size());

		try {
			for(MappableField mappableField : mappableFields) {
				mappableField.getMappingStrategy().mapField(rowModel, mappableField.getField(), contentValues, mappableField.getValueKey());
			}
		} catch(Exception e) {
			throw new RuntimeException("An error occurred while populating ContentValues from model object: " + rowModel, e);
		}

		return contentValues;
	}

	private static class MappableField {
		private String valueKey;
		private Field field;
		private FieldMappingStrategy mappingStrategy;


		public MappableField(String valueKey, Field field, FieldMappingStrategy mappingStrategy) {
			this.valueKey = valueKey;
			this.field = field;
			this.mappingStrategy = mappingStrategy;
		}

		public String getValueKey() {
			return valueKey;
		}

		public Field getField() {
			return field;
		}

		public FieldMappingStrategy getMappingStrategy() {
			return mappingStrategy;
		}
	}

	public static FieldMappingStrategy determineFieldMappingStrategyForClass(Class<?> clazz) {
		if(Boolean.class.isAssignableFrom(clazz)) {
			return new BooleanFieldMappingStrategy();
		} else if(bool.class.isAssignableFrom(clazz)) {
			return new PrimitiveBooleanFieldMappingStrategy();
		} else if(Byte.class.isAssignableFrom(clazz)) {
			return new ByteFieldMappingStrategy();
		} else if(byte.class.isAssignableFrom(clazz)) {
			return new PrimitiveByteFieldMappingStrategy();
		} else if(byte[].class.isAssignableFrom(clazz)) {
			return new PrimitiveByteArrayFieldMappingStrategy();
		} else if(Float.class.isAssignableFrom(clazz)) {
			return new FloatFieldMappingStrategy();
		} else if(float.class.isAssignableFrom(clazz)) {
			return new PrimitiveFloatFieldMappingStrategy();
		} else if(Double.class.isAssignableFrom(clazz)) {
			return new DoubleFieldMappingStrategy();
		} else if(double.class.isAssignableFrom(clazz)) {
			return new PrimitiveDoubleFieldMappingStrategy();
		} else if(Short.class.isAssignableFrom(clazz)) {
			return new ShortFieldMappingStrategy();
		} else if(short.class.isAssignableFrom(clazz)) {
			return new PrimitiveShortFieldMappingStrategy();
		} else if(Integer.class.isAssignableFrom(clazz)) {
			return new IntegerFieldMappingStrategy();
		} else if(int.class.isAssignableFrom(clazz)) {
			return new PrimitiveIntegerFieldMappingStrategy();
		} else if(Long.class.isAssignableFrom(clazz)) {
			return new LongFieldMappingStrategy();
		} else if(long.class.isAssignableFrom(clazz)) {
			return new PrimitiveLongFieldMappingStrategy();
		} else if(String.class.isAssignableFrom(clazz)) {
			return new StringFieldMappingStrategy();
		}

		throw new IllegalArgumentException("Class must be one of the types allowed by ContentProvider.set().");
	}

	private interface FieldMappingStrategy {
		/**
		 * Extracts a value of 'field' from 'modelObject' and inserts it into 'values', under 'valueKey' 
		 * 
		 * @param modelObject Object from which to extract the value for the ContentValues
		 * @param valueKey Key under which to store the value extracted from the field
		 * @param field The field from which to extract the value from
		 * @param values ContentValues to insert the extracted value into
		 */
		void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException;
	}

	private static class PrimitiveBooleanFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getBoolean(modelObject));
		}
	}

	private static class BooleanFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Boolean)field.get(modelObject));
		}
	}

	private static class PrimitiveByteFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getByte(modelObject));
		}
	}

	private static class ByteFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Byte)field.get(modelObject));
		}
	}

	private static class PrimitiveByteArrayFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (byte[])field.get(modelObject));
		}
	}

	private static class PrimitiveFloatFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getFloat(modelObject));
		}
	}

	private static class FloatFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Float)field.get(modelObject));
		}
	}

	private static class PrimitiveDoubleFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getDouble(modelObject));
		}
	}

	private static class DoubleFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Double)field.get(modelObject));
		}
	}

	private static class PrimitiveShortFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getShort(modelObject));
		}
	}

	private static class ShortFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Short)field.get(modelObject));
		}
	}

	private static class PrimitiveIntegerFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getInt(modelObject));
		}
	}

	private static class IntegerFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Integer)field.get(modelObject));
		}
	}

	private static class PrimitiveLongFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getLong(modelObject));
		}
	}

	private static class LongFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Long)field.get(modelObject));
		}
	}

	private static class StringFieldMappingStrategy implements FieldMappingStrategy {
		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (String)field.get(modelObject));
		}
	}
}


