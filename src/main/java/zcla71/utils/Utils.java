package zcla71.utils;

public class Utils {
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
