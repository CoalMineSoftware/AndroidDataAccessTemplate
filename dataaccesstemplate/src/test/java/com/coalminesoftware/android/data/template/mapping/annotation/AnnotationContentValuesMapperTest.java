package com.coalminesoftware.android.data.template.mapping.annotation;

import android.content.ContentValues;

import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.BooleanFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.ByteFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.DoubleFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.FieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.FloatFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.IntegerFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.LongFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.MappedField;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveBooleanFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveByteArrayFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveByteFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveDoubleFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveFloatFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveIntegerFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveLongFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.PrimitiveShortFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.ShortFieldMappingStrategy;
import com.coalminesoftware.android.data.template.mapping.annotation.AnnotationContentValuesMapper.StringFieldMappingStrategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AnnotationContentValuesMapperTest {
	AnnotationContentValuesMapper<Widget> mMapper;

	@Before
	public void setUp() throws Exception {
		mMapper = AnnotationContentValuesMapper.forClass(Widget.class);
	}

	@Test
	public void testMapContentValues() {
		Widget widget = new Widget();

		widget.setPrivateField(1);
		widget.setProtectedField(2);
		widget.setPublicField(3);
		widget.setNamedPrivateField(4);
		widget.setNamedProtectedField(5);
		widget.setNamedPublicField(6);
		widget.setUnmappedField(7);

		widget.setPrivateBaseClassField(8);
		widget.setProtectedBaseClassField(9);
		widget.setPublicBaseClassField(10);
		widget.setNamedPrivateBaseClassField(11);
		widget.setNamedProtectedBaseClassField(12);
		widget.setNamedPublicBaseClassField(13);
		widget.setUnmappedBaseClassField(14);

		ContentValues values = mMapper.mapContentValues(widget);

		assertThat(values.size(), is(12));

		assertThat(values.getAsInteger("privateField"), is(1));
		assertThat(values.getAsInteger("protectedField"), is(2));
		assertThat(values.getAsInteger("publicField"), is(3));
		assertThat(values.getAsInteger("renamedPrivateField"), is(4));
		assertThat(values.getAsInteger("renamedProtectedField"), is(5));
		assertThat(values.getAsInteger("renamedPublicField"), is(6));
		// Field with 7 was purposely not mapped

		assertThat(values.getAsInteger("privateBaseClassField"), is(8));
		assertThat(values.getAsInteger("protectedBaseClassField"), is(9));
		assertThat(values.getAsInteger("publicBaseClassField"), is(10));
		assertThat(values.getAsInteger("renamedPrivateBaseClassField"), is(11));
		assertThat(values.getAsInteger("renamedProtectedBaseClassField"), is(12));
		assertThat(values.getAsInteger("renamedPublicBaseClassField"), is(13));
	}

	@Test
	public void testConstructor() {
		Set<MappedField> fields = mMapper.getMappableFields();

		assertThat(fields, notNullValue());
		assertThat(fields.size(), is(12));

		Map<String,MappedField> fieldsByValueKey = buildMappableFieldsMapByValueKey(fields);

		// Fields from class that rely on the default
		assertThat(fieldsByValueKey.get("privateField"), notNullValue());
		assertThat(fieldsByValueKey.get("protectedField"), notNullValue());
		assertThat(fieldsByValueKey.get("publicField"), notNullValue());

		// Fields from class that specify a name
		assertThat(fieldsByValueKey.get("renamedPrivateField"), notNullValue());
		assertThat(fieldsByValueKey.get("renamedProtectedField"), notNullValue());
		assertThat(fieldsByValueKey.get("renamedPublicField"), notNullValue());

		// Fields from base class that rely on the default
		assertThat(fieldsByValueKey.get("privateBaseClassField"), notNullValue());
		assertThat(fieldsByValueKey.get("protectedBaseClassField"), notNullValue());
		assertThat(fieldsByValueKey.get("publicBaseClassField"), notNullValue());

		// Fields from base class that specify a name
		assertThat(fieldsByValueKey.get("renamedPrivateBaseClassField"), notNullValue());
		assertThat(fieldsByValueKey.get("renamedProtectedBaseClassField"), notNullValue());
		assertThat(fieldsByValueKey.get("renamedPublicBaseClassField"), notNullValue());
	}

	@Test
	public void testDetermineMappingStrategyForFieldType() {
		assertThatMappingStrategyIsOfType(BooleanFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Boolean.class));
		assertThatMappingStrategyIsOfType(PrimitiveBooleanFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(boolean.class));

		assertThatMappingStrategyIsOfType(ByteFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Byte.class));
		assertThatMappingStrategyIsOfType(PrimitiveByteFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(byte.class));

		assertThatMappingStrategyIsOfType(PrimitiveByteArrayFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(byte[].class));

		assertThatMappingStrategyIsOfType(ShortFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Short.class));
		assertThatMappingStrategyIsOfType(PrimitiveShortFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(short.class));

		assertThatMappingStrategyIsOfType(IntegerFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Integer.class));
		assertThatMappingStrategyIsOfType(PrimitiveIntegerFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(int.class));

		assertThatMappingStrategyIsOfType(LongFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Long.class));
		assertThatMappingStrategyIsOfType(PrimitiveLongFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(long.class));

		assertThatMappingStrategyIsOfType(FloatFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Float.class));
		assertThatMappingStrategyIsOfType(PrimitiveFloatFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(float.class));

		assertThatMappingStrategyIsOfType(DoubleFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(Double.class));
		assertThatMappingStrategyIsOfType(PrimitiveDoubleFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(double.class));

		assertThatMappingStrategyIsOfType(StringFieldMappingStrategy.class,
				AnnotationContentValuesMapper.determineMappingStrategyForFieldType(String.class));
	}


	private static void assertThatMappingStrategyIsOfType(Class<? extends FieldMappingStrategy> expectedStrategyClass, FieldMappingStrategy actualMappingStrategy) {
		assertThat(actualMappingStrategy, notNullValue());
		assertThat(expectedStrategyClass.isAssignableFrom(actualMappingStrategy.getClass()),
				is(true));
	}

	private static Map<String,MappedField> buildMappableFieldsMapByValueKey(Set<MappedField> fields) {
		Map<String,MappedField> fieldsByValueKey = new HashMap<String,AnnotationContentValuesMapper.MappedField>(fields.size());

		for(MappedField field : fields) {
			fieldsByValueKey.put(field.getValueKey(), field);
		}

		return fieldsByValueKey;
	}
}
