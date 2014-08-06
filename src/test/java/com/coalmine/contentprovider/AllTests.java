package com.coalmine.contentprovider;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.coalmine.contentprovider.template.ContentProviderClientTemplateTest;
import com.coalmine.contentprovider.template.CursorUtilsTest;
import com.coalmine.contentprovider.template.DeclaredFieldIteratorTest;
import com.coalmine.contentprovider.template.annotation.AnnotationContentValuesMapperTest;
import com.coalmine.contentprovider.template.annotation.AnnotationRowMapperTest;
import com.coalmine.contentprovider.template.naming.UnderscoredNamingStrategyTest;

@RunWith(Suite.class)
@SuiteClasses({
	ContentProviderClientTemplateTest.class,
	CursorUtilsTest.class,
	AnnotationContentValuesMapperTest.class,
	AnnotationRowMapperTest.class,
	UnderscoredNamingStrategyTest.class,
	DeclaredFieldIteratorTest.class })
public class AllTests { }


