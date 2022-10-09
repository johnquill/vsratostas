package handlers;

public class CommandHandler {

    public static String getCommand(String text) {
        String[] arr = text.split("@");
        if (arr.length > 1) {
            String botName = arr[1].split(" ")[0];
            if (!botName.equals("vsratostas")) {
                return "";
            }
        }
        return arr[0].split(" ")[0];
    }
}
