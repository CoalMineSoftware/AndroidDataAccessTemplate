package com.coalminesoftware.cursortemplate.util;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public final class DeprecationUtil {
	private DeprecationUtil() { }

	/**
	 * @return Whether the device is running at least the given API level, or higher.
	 * See {@link android.os.Build.VERSION_CODES} for more readable codename constants.
	 */
	public static boolean hasApiLevel(int requiredLevel) {
		return VERSION.SDK_INT >= requiredLevel;
	}

	public static boolean hasAndroidN() {
		return hasApiLevel(VERSION_CODES.N);
	}
}
