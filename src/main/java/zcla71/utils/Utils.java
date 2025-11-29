package zcla71.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {
	public static String stackTraceToString(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}

	public static Boolean trataNull(Boolean b) {
		return trataNull(b, Boolean.FALSE);
	}

	public static Boolean trataNull(Boolean b, Boolean nullValue) {
		return b == null ? nullValue : b;
	}

	public static Integer trataNull(Integer i) {
		return trataNull(i, 0);
	}

	public static Integer trataNull(Integer i, Integer nullValue) {
		return i == null ? nullValue : i;
	}
}
