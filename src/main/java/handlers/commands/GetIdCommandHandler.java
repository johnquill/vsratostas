package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class GetIdCommandHandler {

    public static final String ID_ANSWER = "Ваш id: %s\nid чата: %s";
    public static SendMessage returnMessage(Message message) {
        String text = String.format(ID_ANSWER, message.getFrom().getId(), message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }
}
