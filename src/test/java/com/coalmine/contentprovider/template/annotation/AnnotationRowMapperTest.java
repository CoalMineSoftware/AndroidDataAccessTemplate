package com.coalmine.contentprovider.template.annotation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.BooleanMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.ByteMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.DoubleMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.FloatMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.IntegerMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.LongMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.MappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveBooleanMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveByteArrayMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveByteMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveDoubleMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveFloatMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveIntegerMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveLongMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.PrimitiveShortMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapper.ShortMappingStrategy;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AnnotationRowMapperTest {
	@Test
	public void testMapRow() {
		AnnotationRowMapper<Widget> mapper = AnnotationRowMapper.forClass(Widget.class);

		Widget widget = mapper.mapRow(buildCursor(), 0);

		assertNotNull(widget);
		assertEquals(1, widget.getPrivateField());
		assertEquals(2, widget.getProtectedField());
		assertEquals(3, widget.getPublicField());
		assertEquals(4, widget.getNamedPrivateField());
		assertEquals(5, widget.getNamedProtectedField());
		assertEquals(6, widget.getNamedPublicField());
		assertEquals(7, widget.getPrivateBaseClassField());
		assertEquals(8, widget.getProtectedBaseClassField());
		assertEquals(9, widget.getPublicBaseClassField());
		assertEquals(10, widget.getNamedPrivateBaseClassField());
		assertEquals(11, widget.getNamedProtectedBaseClassField());
		assertEquals(12, widget.getNamedPublicBaseClassField());
	}

	@Test
	public void testMapRow_privateNoArgConstructorDoesNotCauseExceptionWhenOverridingConstructNewModelObject() {
		AnnotationRowMapper<ClassWithoutPublicNoArgConstructor> mapper = new AnnotationRowMapper<ClassWithoutPublicNoArgConstructor>(ClassWithoutPublicNoArgConstructor.class) {
			protected ClassWithoutPublicNoArgConstructor constructModelObject() {
				// Returns an instance created with the model class's non-no-argument constructor
				return new ClassWithoutPublicNoArgConstructor(0);
			}
		};

		ClassWithoutPublicNoArgConstructor instance = mapper.mapRow(buildCursor(), 0);

		assertNotNull(instance);
	}

	@Test
	public void testConstructNewModelObject() {
		AnnotationRowMapper<Widget> mapper = AnnotationRowMapper.forClass(Widget.class);

		Widget widget = mapper.constructModelObject();

		assertNotNull("Expected an instance of the row model class to have been constructed via reflection.", widget);
	}

	@Test(expected=RuntimeException.class)
	public void testConstructNewModelObject_withoutPublicConstructor() {
		AnnotationRowMapper<ClassWithoutPublicNoArgConstructor> mapper = AnnotationRowMapper.forClass(ClassWithoutPublicNoArgConstructor.class);

		mapper.constructModelObject();
	}

	@Test
	public void testDetermineMappingStrategyForFieldType() {
		assertThatMappingStrategyIsOfType(BooleanMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Boolean.class));
		assertThatMappingStrategyIsOfType(PrimitiveBooleanMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(boolean.class));

		assertThatMappingStrategyIsOfType(ByteMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Byte.class));
		assertThatMappingStrategyIsOfType(PrimitiveByteMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(byte.class));

		assertThatMappingStrategyIsOfType(PrimitiveByteArrayMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(byte[].class));

		assertThatMappingStrategyIsOfType(FloatMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Float.class));
		assertThatMappingStrategyIsOfType(PrimitiveFloatMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(float.class));

		assertThatMappingStrategyIsOfType(DoubleMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Double.class));
		assertThatMappingStrategyIsOfType(PrimitiveDoubleMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(double.class));

		assertThatMappingStrategyIsOfType(ShortMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Short.class));
		assertThatMappingStrategyIsOfType(PrimitiveShortMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(short.class));

		assertThatMappingStrategyIsOfType(IntegerMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Integer.class));
		assertThatMappingStrategyIsOfType(PrimitiveIntegerMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(int.class));

		assertThatMappingStrategyIsOfType(LongMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(Long.class));
		assertThatMappingStrategyIsOfType(PrimitiveLongMappingStrategy.class,
				AnnotationRowMapper.determineMappingStrategyForFieldType(long.class));
	}

	private static Cursor buildCursor() {
		MatrixCursor cursor = new MatrixCursor(new String[] {
				"privateField", "protectedField", "publicField",
				"renamedPrivateField", "renamedProtectedField", "renamedPublicField",
				"privateBaseClassField", "protectedBaseClassField", "publicBaseClassField",
				"renamedPrivateBaseClassField", "renamedProtectedBaseClassField", "renamedPublicBaseClassField"
		});

		cursor.addRow(new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

		cursor.moveToFirst();

		return cursor;
	}

	private static void assertThatMappingStrategyIsOfType(Class<? extends MappingStrategy> expectedStrategyClass, MappingStrategy actualMappingStrategy) {
		assertNotNull("Mapping strategy instance expected",
				actualMappingStrategy);

		assertTrue("Expected mapping strategy to be of type"+expectedStrategyClass.getSimpleName(),
				expectedStrategyClass.isAssignableFrom(actualMappingStrategy.getClass()));
	}

	/** Class without a no-argument constructor for AnnotationRowMapper to call, but with a second
	 * constructor used when overriding {@link AnnotationRowMapper#constructModelObject()}. */
	private static class ClassWithoutPublicNoArgConstructor {
		public ClassWithoutPublicNoArgConstructor(int parameter) { }
	}
}


