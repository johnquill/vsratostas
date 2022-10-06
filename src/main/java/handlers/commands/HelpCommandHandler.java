package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommandHandler {

    public static SendMessage returnMessage(Message message) {
        String text = "Пришли фото с подписью \"стасик\"";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }
}
