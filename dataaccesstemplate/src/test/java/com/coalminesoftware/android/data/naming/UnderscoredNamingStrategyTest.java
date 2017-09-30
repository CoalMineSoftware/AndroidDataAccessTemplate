package com.coalminesoftware.android.data.naming;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UnderscoredNamingStrategyTest {
	@Test
	public void testDetermineName() {
		NamingStrategy strategy = new UnderscoredNamingStrategy();

		String identifierWithCaseChangeInMiddle = "abcDef";
		assertThat(strategy.determineName(identifierWithCaseChangeInMiddle), is("abc_def"));

		String identifierStartingWithCapital = "AbcDef";
		assertThat(strategy.determineName(identifierStartingWithCapital), is("abc_def"));

		String identifierWithCapitalAtEnd = "abcdeF";
		assertThat(strategy.determineName(identifierWithCapitalAtEnd), is("abcde_f"));

		String identifierWithConsecutiveCapitals = "abCDef";
		assertThat(strategy.determineName(identifierWithConsecutiveCapitals), is("ab_c_def"));

		String identifierWithNumber = "strictI18n";
		assertThat(strategy.determineName(identifierWithNumber), is("strict_i18n"));
	}
}
