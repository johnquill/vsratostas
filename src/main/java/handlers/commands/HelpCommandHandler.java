package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommandHandler {

    public static final String HELP = "Пришли фото с подписью \"Cтасик\"\n\n" +
            "/add_caption новая подпись - предложить подпись \"новая подпись\"\n";

    public static SendMessage returnMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(HELP);
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }
}
