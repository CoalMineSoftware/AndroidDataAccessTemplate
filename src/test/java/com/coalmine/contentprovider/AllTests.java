package com.coalmine.contentprovider;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.coalmine.contentprovider.template.ContentProviderClientTemplateTest;
import com.coalmine.contentprovider.template.CursorUtilsTest;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapperTest;


@RunWith(Suite.class)
@SuiteClasses({
	ContentProviderClientTemplateTest.class,
	CursorUtilsTest.class,
	AnnotationContentValuesMapperTest.class })
public class AllTests { }


