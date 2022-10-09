package stasik;

public enum WorkSpace {
    ADMIN,
    USER,
    CHAT;

    public static boolean isAdmin(String chatId) {
        return chatId.equals(StasikBot.ADMIN_ID);
    }
}
