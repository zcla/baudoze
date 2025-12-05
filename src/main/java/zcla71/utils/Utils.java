package zcla71.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Utils {
	public static void copiaPropriedades(Object source, Object target) {
		for (Field sourceField : source.getClass().getDeclaredFields()) {
			if (!Modifier.isStatic(sourceField.getModifiers())) {
				try {
					sourceField.setAccessible(true);
					Field targetField = Arrays.stream(target.getClass().getDeclaredFields()).filter((f) -> f.getName().equals(sourceField.getName())).findFirst().get();
					targetField.setAccessible(true);
					targetField.set(target, sourceField.get(source));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// ignora
				}
			}
		}
	}

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
