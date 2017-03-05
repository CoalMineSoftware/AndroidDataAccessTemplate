package com.coalminesoftware.cursortemplate.annotation;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;

import com.coalminesoftware.cursortemplate.ContentValuesMapper;
import com.coalminesoftware.cursortemplate.DeclaredFieldIterator;
import com.coalminesoftware.cursortemplate.naming.DefaultNamingStrategy;
import com.coalminesoftware.cursortemplate.naming.NamingStrategy;

/** A {@link ContentValuesMapper} implementation that maps all of the model object's fields
 * (including inherited fields) that are annotated with {@link Column}.  A field's
 * {@link Column#name()} is used as the key of the value inserted into the generated ContentValues.
 * If a name is not provided, the mapper's keyNamingStrategy is used to determine the corresponding
 * key name based on the field's name. */
public class AnnotationContentValuesMapper<RowModel> implements ContentValuesMapper<RowModel> {
	private Set<MappedField> mMappedFields = new HashSet<>();
	private NamingStrategy keyNamingStrategy = new DefaultNamingStrategy();

	public AnnotationContentValuesMapper(Class<RowModel> modelClass) {
		DeclaredFieldIterator fieldIterator = new DeclaredFieldIterator(modelClass);
		while(fieldIterator.hasNext()) {
			Field field = fieldIterator.next();

			if(field.isAnnotationPresent(Column.class)) {
				makeFieldAccessible(field);

				String valueKey = determineKey(
						field.getAnnotation(Column.class).name(),
						field.getName());

				mMappedFields.add(new MappedField(valueKey, field,
						determineMappingStrategyForFieldType(field.getType())));
			}
		}
	}

	private void makeFieldAccessible(Field field) {
		if(!field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/** Determines the name under which a Field's value will be stored in a ContentValues. */
	private String determineKey(String annotatedName, String fieldName) {
		return Column.DEFAULT_NAME.equals(annotatedName)?
				keyNamingStrategy.determineName(fieldName) :
				annotatedName;
	}

	@Override
	public ContentValues mapContentValues(RowModel rowModel) {
		ContentValues contentValues = new ContentValues(mMappedFields.size());

		try {
			for(MappedField mappedField : mMappedFields) {
				mappedField.getMappingStrategy().mapField(
						rowModel, mappedField.getField(),
						contentValues, mappedField.getValueKey());
			}
		} catch(Exception e) {
			throw new RuntimeException("An error occurred while populating ContentValues from model object: " + rowModel, e);
		}

		return contentValues;
	}

	protected Set<MappedField> getMappableFields() {
		return mMappedFields;
	}

	protected static class MappedField {
		private String valueKey;
		private Field field;
		private FieldMappingStrategy mappingStrategy;


		public MappedField(String valueKey, Field field, FieldMappingStrategy mappingStrategy) {
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

	protected static FieldMappingStrategy determineMappingStrategyForFieldType(Class<?> fieldClass) {
		if(Boolean.class.isAssignableFrom(fieldClass)) {
			return BooleanFieldMappingStrategy.getInstance();
		} else if(boolean.class.isAssignableFrom(fieldClass)) {
			return PrimitiveBooleanFieldMappingStrategy.getInstance();
		} else if(Byte.class.isAssignableFrom(fieldClass)) {
			return ByteFieldMappingStrategy.getInstance();
		} else if(byte.class.isAssignableFrom(fieldClass)) {
			return PrimitiveByteFieldMappingStrategy.getInstance();
		} else if(byte[].class.isAssignableFrom(fieldClass)) {
			return PrimitiveByteArrayFieldMappingStrategy.getInstance();
		} else if(Float.class.isAssignableFrom(fieldClass)) {
			return FloatFieldMappingStrategy.getInstance();
		} else if(float.class.isAssignableFrom(fieldClass)) {
			return PrimitiveFloatFieldMappingStrategy.getInstance();
		} else if(Double.class.isAssignableFrom(fieldClass)) {
			return DoubleFieldMappingStrategy.getInstance();
		} else if(double.class.isAssignableFrom(fieldClass)) {
			return PrimitiveDoubleFieldMappingStrategy.getInstance();
		} else if(Short.class.isAssignableFrom(fieldClass)) {
			return ShortFieldMappingStrategy.getInstance();
		} else if(short.class.isAssignableFrom(fieldClass)) {
			return PrimitiveShortFieldMappingStrategy.getInstance();
		} else if(Integer.class.isAssignableFrom(fieldClass)) {
			return IntegerFieldMappingStrategy.getInstance();
		} else if(int.class.isAssignableFrom(fieldClass)) {
			return PrimitiveIntegerFieldMappingStrategy.getInstance();
		} else if(Long.class.isAssignableFrom(fieldClass)) {
			return LongFieldMappingStrategy.getInstance();
		} else if(long.class.isAssignableFrom(fieldClass)) {
			return PrimitiveLongFieldMappingStrategy.getInstance();
		} else if(String.class.isAssignableFrom(fieldClass)) {
			return StringFieldMappingStrategy.getInstance();
		}

		throw new IllegalArgumentException("Class must be one of the types allowed by ContentProvider.set().  Class was "+fieldClass.getSimpleName());
	}

	/** Sets the strategy used to determine the name under which a field's value is stored in a
	 * ContentValues object when one is not specified on the field's {@link Column} annotation.
	 * An instance of {@link DefaultNamingStrategy} is used by default. */
	public void setKeyNamingStrategy(NamingStrategy keyNamingStrategy) {
		this.keyNamingStrategy = keyNamingStrategy;
	}


	protected interface FieldMappingStrategy {
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

	protected static class PrimitiveBooleanFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveBooleanFieldMappingStrategy instance;

		public static PrimitiveBooleanFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveBooleanFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getBoolean(modelObject));
		}
	}

	protected static class BooleanFieldMappingStrategy implements FieldMappingStrategy {
		private static BooleanFieldMappingStrategy instance;

		public static BooleanFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new BooleanFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Boolean)field.get(modelObject));
		}
	}

	protected static class PrimitiveByteFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveByteFieldMappingStrategy instance;

		public static PrimitiveByteFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveByteFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getByte(modelObject));
		}
	}

	protected static class ByteFieldMappingStrategy implements FieldMappingStrategy {
		private static ByteFieldMappingStrategy instance;

		public static ByteFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new ByteFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Byte)field.get(modelObject));
		}
	}

	protected static class PrimitiveByteArrayFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveByteArrayFieldMappingStrategy instance;

		public static PrimitiveByteArrayFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveByteArrayFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (byte[])field.get(modelObject));
		}
	}

	protected static class PrimitiveFloatFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveFloatFieldMappingStrategy instance;

		public static PrimitiveFloatFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveFloatFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getFloat(modelObject));
		}
	}

	protected static class FloatFieldMappingStrategy implements FieldMappingStrategy {
		private static FloatFieldMappingStrategy instance;

		public static FloatFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new FloatFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Float)field.get(modelObject));
		}
	}

	protected static class PrimitiveDoubleFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveDoubleFieldMappingStrategy instance;

		public static PrimitiveDoubleFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveDoubleFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getDouble(modelObject));
		}
	}

	protected static class DoubleFieldMappingStrategy implements FieldMappingStrategy {
		private static DoubleFieldMappingStrategy instance;

		public static DoubleFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new DoubleFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Double)field.get(modelObject));
		}
	}

	protected static class PrimitiveShortFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveShortFieldMappingStrategy instance;

		public static PrimitiveShortFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveShortFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getShort(modelObject));
		}
	}

	protected static class ShortFieldMappingStrategy implements FieldMappingStrategy {
		private static ShortFieldMappingStrategy instance;

		public static ShortFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new ShortFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Short)field.get(modelObject));
		}
	}

	protected static class PrimitiveIntegerFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveIntegerFieldMappingStrategy instance;

		public static PrimitiveIntegerFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveIntegerFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getInt(modelObject));
		}
	}

	protected static class IntegerFieldMappingStrategy implements FieldMappingStrategy {
		private static IntegerFieldMappingStrategy instance;

		public static IntegerFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new IntegerFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Integer)field.get(modelObject));
		}
	}

	protected static class PrimitiveLongFieldMappingStrategy implements FieldMappingStrategy {
		private static PrimitiveLongFieldMappingStrategy instance;

		public static PrimitiveLongFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new PrimitiveLongFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, field.getLong(modelObject));
		}
	}

	protected static class LongFieldMappingStrategy implements FieldMappingStrategy {
		private static LongFieldMappingStrategy instance;

		public static LongFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new LongFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (Long)field.get(modelObject));
		}
	}

	protected static class StringFieldMappingStrategy implements FieldMappingStrategy {
		private static StringFieldMappingStrategy instance;

		public static StringFieldMappingStrategy getInstance() {
			if(instance==null) {
				instance = new StringFieldMappingStrategy();
			}
			return instance;
		}

		@Override
		public void mapField(Object modelObject, Field field, ContentValues values, String valueKey) throws IllegalArgumentException, IllegalAccessException {
			values.put(valueKey, (String)field.get(modelObject));
		}
	}

	/** Convenience method for instantiating a mapper without having to provide
	 * the row model class twice, as both a generic parameter and as a parameter
	 * to {@link #AnnotationContentValuesMapper(Class)}. */
	public static <Type> AnnotationContentValuesMapper<Type> forClass(Class<Type> rowModelType) {
		return new AnnotationContentValuesMapper<Type>(rowModelType);
	}
}
