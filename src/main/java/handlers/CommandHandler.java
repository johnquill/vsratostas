package handlers;

import handlers.commands.GetIdCommandHandler;
import handlers.commands.HelpCommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandHandler {
    public static SendMessage handle(Message message) {
        switch (getCommand(message.getText())) {
            case "/help":
                return HelpCommandHandler.returnMessage(message);
            case "/get_id":
                return GetIdCommandHandler.returnMessage(message);
            default:
                return null;
        }
    }

    private static String getCommand(String text) {
        String[] arr = text.split("@");
        if (arr.length > 1 && !arr[1].equals("vsratostas")) {
            return "";
        }
        return arr[0];
    }
}
