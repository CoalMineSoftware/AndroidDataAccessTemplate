package com.coalmine.contentprovider.template.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.ContentValues;

import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.BooleanFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.ByteFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.DoubleFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.FieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.FloatFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.IntegerFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.LongFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.MappedField;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveBooleanFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveByteArrayFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveByteFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveDoubleFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveFloatFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveIntegerFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveLongFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.PrimitiveShortFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.ShortFieldMappingStrategy;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapper.StringFieldMappingStrategy;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AnnotationContentValuesMapperTest {
	AnnotationContentValuesMapper<Widget> mapper;

	@Before
	public void setUp() throws Exception {
		mapper = new AnnotationContentValuesMapper<Widget>(Widget.class);
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

		ContentValues values = mapper.mapContentValues(widget);

		assertEquals("Expected values from the fields annotated with @ContentValue", 12, values.size());

		assertEquals((Integer)1, values.getAsInteger("privateField"));
		assertEquals((Integer)2, values.getAsInteger("protectedField"));
		assertEquals((Integer)3, values.getAsInteger("publicField"));
		assertEquals((Integer)4, values.getAsInteger("renamedPrivateField"));
		assertEquals((Integer)5, values.getAsInteger("renamedProtectedField"));
		assertEquals((Integer)6, values.getAsInteger("renamedPublicField"));

		assertEquals((Integer)8, values.getAsInteger("privateBaseClassField"));
		assertEquals((Integer)9, values.getAsInteger("protectedBaseClassField"));
		assertEquals((Integer)10, values.getAsInteger("publicBaseClassField"));
		assertEquals((Integer)11, values.getAsInteger("renamedPrivateBaseClassField"));
		assertEquals((Integer)12, values.getAsInteger("renamedProtectedBaseClassField"));
		assertEquals((Integer)13, values.getAsInteger("renamedPublicBaseClassField"));
	}

	@Test
	public void testConstructor() {
		Set<MappedField> fields = mapper.getMappableFields();

		assertNotNull(fields);
		assertEquals(12, fields.size());

		Map<String,MappedField> fieldsByValueKey = buildMappableFieldsMapByValueKey(fields);

		// Fields from class that rely on the default
		assertNotNull(fieldsByValueKey.get("privateField"));
		assertNotNull(fieldsByValueKey.get("protectedField"));
		assertNotNull(fieldsByValueKey.get("publicField"));

		// Fields from class that specify a name
		assertNotNull(fieldsByValueKey.get("renamedPrivateField"));
		assertNotNull(fieldsByValueKey.get("renamedProtectedField"));
		assertNotNull(fieldsByValueKey.get("renamedPublicField"));

		// Fields from base class that rely on the default
		assertNotNull(fieldsByValueKey.get("privateBaseClassField"));
		assertNotNull(fieldsByValueKey.get("protectedBaseClassField"));
		assertNotNull(fieldsByValueKey.get("publicBaseClassField"));

		// Fields from base class that specify a name
		assertNotNull(fieldsByValueKey.get("renamedPrivateBaseClassField"));
		assertNotNull(fieldsByValueKey.get("renamedProtectedBaseClassField"));
		assertNotNull(fieldsByValueKey.get("renamedPublicBaseClassField"));
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
		assertNotNull("Field mapping strategy instance expected",
				actualMappingStrategy);

		assertTrue("Expected mapping strategy to be of type"+expectedStrategyClass.getSimpleName(),
				expectedStrategyClass.isAssignableFrom(actualMappingStrategy.getClass()));
	}

	private static Map<String,MappedField> buildMappableFieldsMapByValueKey(Set<MappedField> fields) {
		Map<String,MappedField> fieldsByValueKey = new HashMap<String,AnnotationContentValuesMapper.MappedField>(fields.size());

		for(MappedField field : fields) {
			fieldsByValueKey.put(field.getValueKey(), field);
		}

		return fieldsByValueKey;
	}
}


