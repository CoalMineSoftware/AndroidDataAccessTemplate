package com.coalmine.contentprovider.template.naming;

import java.util.Locale;
import java.util.regex.Pattern;

/** Naming strategy that converts the given name (which is assumed to use camel case) to
 * underscore-delimited lower case.  For example, an input of "someField" would return
 * "some_field".
 * <p>
 * An underscore is inserted after any letter, regardless of case, that is followed by an uppercase
 * letter. This strategy ignores numbers in the input.  This allows conversions like "strictI18n"
 * to "strict_i18n" and "hemoglobinA1c" to "hemoglobin_a1c", while not handling (arguably poor)
 * names like "textInput2", which would be converted to "text_input2", not "text_input_2". */
public class UnderscoredNamingStrategy implements NamingStrategy {
	private static final Pattern CASE_SWITCH_PATTERN = Pattern.compile("([a-zA-Z])(?=[A-Z])");

	public String determineName(String fieldName) {
		return CASE_SWITCH_PATTERN.matcher(fieldName)
				.replaceAll("$1_")
				.toLowerCase(Locale.ROOT);
	}
}


