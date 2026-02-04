package menupack;

public class UserSession {

    private static String username;
    private static String userType;
    private static String serverIp;


    // Prevent instantiation
    private UserSession() {}

    public static void setSession(String user, String type) {
        username = user;
        userType = type;
    }

    public static String getUsername() {
        return username;
    }

    public static String getUserType() {
        return userType;
    }

    public static boolean isLoggedIn() {
        return username != null && !username.isEmpty();
    }
    public static void setServerIp(String ip) {
        serverIp = ip;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static void clearSession() {
        username = null;
        userType = null;
    }
}
