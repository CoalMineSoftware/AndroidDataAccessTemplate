package com.coalmine.contentprovider.template.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.DeclaredFieldIterator;
import com.coalmine.contentprovider.template.RowMapper;
import com.coalmine.contentprovider.template.naming.DefaultNamingStrategy;
import com.coalmine.contentprovider.template.naming.NamingStrategy;

/** A {@link RowMapper} implementation that populates all of the model class's fields (including
 * inherited fields) that are annotated with {@link Column}.  Each field's {@link Column#name()}
 * value is used as name of the column from which the field will be populated.  If a name is
 * not provided, the mapper's columnNamingStrategy is used to determine the corresponding column
 * name based on the field's name. */
public class AnnotationRowMapper<RowModel> implements RowMapper<RowModel> {
	private Class<RowModel> rowModelClass;
	private NamingStrategy columnNamingStrategy = new DefaultNamingStrategy();
	private Set<MappedField> mappedFields = new HashSet<MappedField>();
	private Constructor<RowModel> rowModelConstructor;

	public AnnotationRowMapper(Class<RowModel> rowModelClass) {
		this.rowModelClass = rowModelClass;

		DeclaredFieldIterator fieldIterator = new DeclaredFieldIterator(rowModelClass);
		while(fieldIterator.hasNext()) {
			Field field = fieldIterator.next();

			if(field.isAnnotationPresent(Column.class)) {
				if(!field.isAccessible()) {
					field.setAccessible(true);
				}

				Column columnAnnotation = field.getAnnotation(Column.class);

				String columnName = columnAnnotation.name();
				if(Column.DEFAULT_NAME.equals(columnName)) {
					columnName = columnNamingStrategy.determineName(field.getName());
				}

				mappedFields.add(new MappedField(columnName, field,
						determineMappingStrategyForFieldType(field.getType())));
			}
		}
	}

	@Override
	public RowModel mapRow(Cursor cursor, int rowNumber) {
		RowModel model = constructModelObject();

		try {
			for(MappedField mappedField : mappedFields) {
				mappedField.getMappingStrategy().mapColumn(
						cursor,
						mappedField.getColumnName(),
						model,
						mappedField.getField());
			}
		} catch(Exception e) {
			throw new RuntimeException("An error occurred while populating model object from Cursor", e);
		}

		return model;
	}

	/** Used to get a new instance of the mapper's row model, for each row in the {@link Cursor}
	 * being mapped.  This method creates an instance via reflection, using the class's no argument
	 * constructor.  Users may choose to override this method to instantiate an instance themselves
	 * to avoid the performance penalty of using reflection. */
	protected RowModel constructModelObject() {
		try {
			return getOrRetrieveRowModelConstructor().newInstance();
		} catch(Exception e) {
			throw new RuntimeException("Unable to instantiate instance of row model type, "+rowModelConstructor.getDeclaringClass(), e);
		}
	}

	/** Because users may choose to override {@link #constructModelObject()} rather than relying
	 * on reflection, the row model class's constructor is retrieved lazily, to avoid throwing a
	 * {@link NoSuchMethodException} or {@link SecurityException} if the constructor will never
	 * be used. This method returns the value of rowModelConstructor, first retrieving it if
	 * necessary. */
	private Constructor<RowModel> getOrRetrieveRowModelConstructor() {
		if(rowModelConstructor == null) {
			try {
				rowModelConstructor = rowModelClass.getConstructor();
			} catch(Exception e) {
				throw new IllegalArgumentException("The row model class does not appear to have a public no-argument constructor", e);
			}
		}

		return rowModelConstructor;
	}

	protected static MappingStrategy determineMappingStrategyForFieldType(Class<?> type) {
		if(Boolean.class.equals(type)) {
			return BooleanMappingStrategy.getInstance();
		} else if(boolean.class.equals(type)) {
			return PrimitiveBooleanMappingStrategy.getInstance();
		} else if(Byte.class.isAssignableFrom(type)) {
			return ByteMappingStrategy.getInstance();
		} else if(byte.class.isAssignableFrom(type)) {
			return PrimitiveByteMappingStrategy.getInstance();
		} else if(byte[].class.isAssignableFrom(type)) {
			return PrimitiveByteArrayMappingStrategy.getInstance();
		} else if(Float.class.isAssignableFrom(type)) {
			return FloatMappingStrategy.getInstance();
		} else if(float.class.isAssignableFrom(type)) {
			return PrimitiveFloatMappingStrategy.getInstance();
		} else if(Double.class.isAssignableFrom(type)) {
			return DoubleMappingStrategy.getInstance();
		} else if(double.class.isAssignableFrom(type)) {
			return PrimitiveDoubleMappingStrategy.getInstance();
		} else if(Short.class.isAssignableFrom(type)) {
			return ShortMappingStrategy.getInstance();
		} else if(short.class.isAssignableFrom(type)) {
			return PrimitiveShortMappingStrategy.getInstance();
		} else if(Integer.class.isAssignableFrom(type)) {
			return IntegerMappingStrategy.getInstance();
		} else if(int.class.isAssignableFrom(type)) {
			return PrimitiveIntegerMappingStrategy.getInstance();
		} else if(Long.class.isAssignableFrom(type)) {
			return LongMappingStrategy.getInstance();
		} else if(long.class.isAssignableFrom(type)) {
			return PrimitiveLongMappingStrategy.getInstance();
		} else if(String.class.isAssignableFrom(type)) {
			return StringMappingStrategy.getInstance();
		}

		throw new IllegalArgumentException("Class must be one of the types that can be retrieved from a Cursor.  Class was "+type.getSimpleName());
	}

	private static class MappedField {
		private String columnName;
		private Field field;
		private MappingStrategy mappingStrategy;
	
		public MappedField(String columnName, Field field, MappingStrategy mappingStrategy) {
			this.columnName = columnName;
			this.field = field;
			this.mappingStrategy = mappingStrategy;
		}

		public String getColumnName() {
			return columnName;
		}

		public Field getField() {
			return field;
		}

		public MappingStrategy getMappingStrategy() {
			return mappingStrategy;
		}
	}

	protected interface MappingStrategy {
		void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException;
	}

	protected static class BooleanMappingStrategy implements MappingStrategy {
		private static BooleanMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getBoolean(cursor, columnName));
		}

		public static BooleanMappingStrategy getInstance() {
			if(instance == null) {
				instance = new BooleanMappingStrategy();
			}
			return instance;
		}
	}

	protected static class PrimitiveBooleanMappingStrategy implements MappingStrategy {
		private static PrimitiveBooleanMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredBoolean(cursor, columnName));
		}

		public static PrimitiveBooleanMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveBooleanMappingStrategy();
			}

			return instance;
		}
	}

	protected static class ByteMappingStrategy implements MappingStrategy {
		private static ByteMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getByte(cursor, columnName));
		}

		public static ByteMappingStrategy getInstance() {
			if(instance == null) {
				instance = new ByteMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveByteMappingStrategy implements MappingStrategy {
		private static PrimitiveByteMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredByte(cursor, columnName));
		}

		public static PrimitiveByteMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveByteMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveByteArrayMappingStrategy implements MappingStrategy {
		private static PrimitiveByteArrayMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getBlob(cursor, columnName));
		}

		public static PrimitiveByteArrayMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveByteArrayMappingStrategy();
			}

			return instance;
		}
	}

	protected static class FloatMappingStrategy implements MappingStrategy {
		private static FloatMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getFloat(cursor, columnName));
		}

		public static FloatMappingStrategy getInstance() {
			if(instance == null) {
				instance = new FloatMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveFloatMappingStrategy implements MappingStrategy {
		private static PrimitiveFloatMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredFloat(cursor, columnName));
		}

		public static PrimitiveFloatMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveFloatMappingStrategy();
			}

			return instance;
		}
	}

	protected static class DoubleMappingStrategy implements MappingStrategy {
		private static DoubleMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getDouble(cursor, columnName));
		}

		public static DoubleMappingStrategy getInstance() {
			if(instance == null) {
				instance = new DoubleMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveDoubleMappingStrategy implements MappingStrategy {
		private static PrimitiveDoubleMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredDouble(cursor, columnName));
		}

		public static PrimitiveDoubleMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveDoubleMappingStrategy();
			}

			return instance;
		}
	}

	protected static class ShortMappingStrategy implements MappingStrategy {
		private static ShortMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getShort(cursor, columnName));
		}

		public static ShortMappingStrategy getInstance() {
			if(instance == null) {
				instance = new ShortMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveShortMappingStrategy implements MappingStrategy {
		private static PrimitiveShortMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredShort(cursor, columnName));
		}

		public static PrimitiveShortMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveShortMappingStrategy();
			}

			return instance;
		}
	}

	protected static class IntegerMappingStrategy implements MappingStrategy {
		private static IntegerMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getInteger(cursor, columnName));
		}

		public static IntegerMappingStrategy getInstance() {
			if(instance == null) {
				instance = new IntegerMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveIntegerMappingStrategy implements MappingStrategy {
		private static PrimitiveIntegerMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredInteger(cursor, columnName));
		}

		public static PrimitiveIntegerMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveIntegerMappingStrategy();
			}

			return instance;
		}
	}

	protected static class LongMappingStrategy implements MappingStrategy {
		private static LongMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getLong(cursor, columnName));
		}

		public static LongMappingStrategy getInstance() {
			if(instance == null) {
				instance = new LongMappingStrategy();
			}

			return instance;
		}
	}

	protected static class PrimitiveLongMappingStrategy implements MappingStrategy {
		private static PrimitiveLongMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getRequiredLong(cursor, columnName));
		}

		public static PrimitiveLongMappingStrategy getInstance() {
			if(instance == null) {
				instance = new PrimitiveLongMappingStrategy();
			}

			return instance;
		}
	}

	protected static class StringMappingStrategy implements MappingStrategy {
		private static StringMappingStrategy instance;

		@Override
		public void mapColumn(Cursor cursor, String columnName, Object modelObject, Field field) throws IllegalArgumentException, IllegalAccessException {
			field.set(modelObject, CursorUtils.getString(cursor, columnName));
		}

		public static StringMappingStrategy getInstance() {
			if(instance == null) {
				instance = new StringMappingStrategy();
			}

			return instance;
		}
	}

	/** Convenience method for instantiating an AnnotationRowMapper without having to provide the
	 * row model class twice, as both a generic parameter and as a parameter to
	 * {@link #AnnotationRowMapper(Class)}. */
	public static <Type> AnnotationRowMapper<Type> forClass(Class<Type> rowModelClass) {
		return new AnnotationRowMapper<Type>(rowModelClass);
	}
}


