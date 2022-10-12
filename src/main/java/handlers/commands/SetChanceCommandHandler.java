package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.ChatsAndChances;

public class SetChanceCommandHandler {

    public static final String ANSWER = "Шанс случайного срабатывания бота установлен на %s процентов";

    public static SendMessage returnMessage(Message message) {
        int value = Integer.parseInt(message.getText().split(" ")[1]);
        if (value < 0) {
            value = 0;
        } else if (value > 100) {
            value = 100;
        }
        ChatsAndChances.editChances(message.getChatId(), value);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format(ANSWER, value))
                .build();
    }
}
