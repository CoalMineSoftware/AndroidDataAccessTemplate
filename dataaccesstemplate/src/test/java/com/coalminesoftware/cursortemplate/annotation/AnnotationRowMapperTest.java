package com.coalminesoftware.cursortemplate.annotation;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.BooleanMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.ByteMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.DoubleMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.FloatMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.IntegerMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.LongMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.MappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveBooleanMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveByteArrayMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveByteMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveDoubleMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveFloatMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveIntegerMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveLongMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.PrimitiveShortMappingStrategy;
import com.coalminesoftware.cursortemplate.annotation.AnnotationRowMapper.ShortMappingStrategy;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AnnotationRowMapperTest {
	@Test
	public void testMapRow() {
		AnnotationRowMapper<Widget> mapper = AnnotationRowMapper.forClass(Widget.class);

		Widget widget = mapper.mapRow(buildCursor(), 0);

		assertThat(widget, notNullValue());
		assertThat(widget.getPrivateField(), is(1));
		assertThat(widget.getProtectedField(), is(2));
		assertThat(widget.getPublicField(), is(3));
		assertThat(widget.getNamedPrivateField(), is(4));
		assertThat(widget.getNamedProtectedField(), is(5));
		assertThat(widget.getNamedPublicField(), is(6));
		assertThat(widget.getPrivateBaseClassField(), is(7));
		assertThat(widget.getProtectedBaseClassField(), is(8));
		assertThat(widget.getPublicBaseClassField(), is(9));
		assertThat(widget.getNamedPrivateBaseClassField(), is(10));
		assertThat(widget.getNamedProtectedBaseClassField(), is(11));
		assertThat(widget.getNamedPublicBaseClassField(), is(12));
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

		assertThat(instance, notNullValue());
	}

	@Test
	public void testConstructNewModelObject() {
		AnnotationRowMapper<Widget> mapper = AnnotationRowMapper.forClass(Widget.class);

		Widget widget = mapper.constructModelObject();

		assertThat(widget, notNullValue());
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
		assertThat(actualMappingStrategy, notNullValue());

		assertThat(expectedStrategyClass.isAssignableFrom(actualMappingStrategy.getClass()), is(true));
	}

	/** Class without a no-argument constructor for AnnotationRowMapper to call, but with a second
	 * constructor used when overriding {@link AnnotationRowMapper#constructModelObject()}. */
	private static class ClassWithoutPublicNoArgConstructor {
		public ClassWithoutPublicNoArgConstructor(int parameter) { }
	}
}
