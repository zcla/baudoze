package zcla71.baudoze.common.config;

public final class UserContext {
    private static Long currentUserId = 1L;

    public static Long getUserId() {
        return currentUserId;
    }

    public static void setUserId(Long userId) {
        currentUserId = userId;
    }
}
