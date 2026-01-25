package zcla71.baudoze.tarefa.service.chatgpt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class UserFileIds {
    private UserFileIds() {}

    public static String emailHashUrlSafe(String email) {
        String norm = normalizeEmail(email);
        byte[] digest = sha256(norm.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    public static String normalizeEmail(String email) {
        if (email == null) throw new IllegalArgumentException("email is null");
        return email.trim().toLowerCase();
    }

    private static byte[] sha256(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
