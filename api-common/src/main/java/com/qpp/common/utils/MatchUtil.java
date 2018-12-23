package com.qpp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {

	public static boolean match(String source, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		return matcher.matches();
	}
}
