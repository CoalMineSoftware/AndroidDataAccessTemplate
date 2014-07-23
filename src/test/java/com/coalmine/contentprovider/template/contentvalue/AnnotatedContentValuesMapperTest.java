package com.coalmine.contentprovider.template.contentvalue;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AnnotatedContentValuesMapperTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testMapContentValues() {
		fail("Not yet implemented");
	}

	@Test
	public void testDetermineFieldMappingStrategyForClass() {
		fail("Not yet implemented");
	}


	private class Widget extends BaseWidget {
		
	}

	private class BaseWidget {
		
	}
}


