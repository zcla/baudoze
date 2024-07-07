package zcla71.baudoze.controller;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    // TODO Fazer testes unitários

    public static void checkIsbn10(String value, String mensagem, String campo) throws ValidationException {
        // https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s13.html
        if ((value == null) || (value.trim().length() == 0)) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        String digito = value.substring(9, 10);
        value = new StringBuilder().append(value).reverse().toString().substring(1);
        int sum = 0;
        for (int i = 0; i < value.length(); i++) {
            sum += (i + 2) * Integer.parseInt(value.substring(i, i + 1), 10);
        }
        Integer intCheck = 11 - (sum % 11);
        String check = intCheck.toString();
        if (intCheck == 10) {
            check = "X";
        } else if (intCheck == 11) {
            check = "0";
        }
        if (!digito.equals(check.toString())) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkIsbn13(String value, String mensagem, String campo) throws ValidationException {
        // https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s13.html
        if ((value == null) || (value.trim().length() == 0)) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        String digito = value.substring(12, 13);
        int sum = 0;
        for (int i = 0; i < value.length() - 1; i++) {
            sum += (i % 2 * 2 + 1) * Integer.parseInt(value.substring(i, i + 1), 10);
        }
        Integer check = 10 - (sum % 10);
        if (check == 10) {
            check = 0;
        }
        if (!digito.equals(check.toString())) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkLessThanOrEqual(Integer value, Integer limit, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        if (value > limit) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkLessThan(Integer value, Integer limit, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        if (value >= limit) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkMoreThanOrEqual(Integer value, Integer limit, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        if (value < limit) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkMoreThan(Integer value, Integer limit, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        if (value <= limit) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkNotEmpty(Collection<?> value, String mensagem, String campo) throws ValidationException {
        if ((value == null) || (value.size() == 0)) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkNotEmpty(Integer value, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkNotEmpty(String value, String mensagem, String campo) throws ValidationException {
        if ((value == null) || (value.trim().length() == 0)) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkRegex(String value, String regex, String mensagem, String campo) throws ValidationException {
        if ((value == null) || (value.trim().length() == 0)) {
            return; // Retorna sempre verdadeiro; se quiser essa verificação, use também o checkNotEmpty()
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            throw new ValidationException(mensagem, campo);
        }
    }
}
