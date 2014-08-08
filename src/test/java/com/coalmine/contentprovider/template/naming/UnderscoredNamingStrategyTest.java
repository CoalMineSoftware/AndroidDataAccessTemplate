package com.coalmine.contentprovider.template.naming;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnderscoredNamingStrategyTest {
	@Test
	public void testDetermineName() {
		NamingStrategy strategy = new UnderscoredNamingStrategy();

		String identifierWithCaseChangeInMiddle = "abcDef";
		assertEquals("abc_def", strategy.determineName(identifierWithCaseChangeInMiddle));

		String identifierStartingWithCapital = "AbcDef";
		assertEquals("abc_def", strategy.determineName(identifierStartingWithCapital));

		String identifierWithCapitalAtEnd = "abcdeF";
		assertEquals("abcde_f", strategy.determineName(identifierWithCapitalAtEnd));

		String identifierWithConsecutiveCapitals = "abCDef";
		assertEquals("ab_c_def", strategy.determineName(identifierWithConsecutiveCapitals));

		String identifierWithNumber = "strictI18n";
		assertEquals("strict_i18n", strategy.determineName(identifierWithNumber));
	}
}


