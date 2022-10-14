package handlers.commands;

import controllers.UserStatusDefiner;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.ChatsAndChances;

public class SetChanceCommandHandler {

    public static final String SUCCESS_ANSWER = "Шанс случайного срабатывания бота установлен на %s процентов";
    public static final String NOT_ADMIN_ANSWER = "Устанавливать шанс может только администратор";

    public static SendMessage returnMessage(Message message) {
        SendMessage.SendMessageBuilder sendMessage = SendMessage.builder()
                .chatId(message.getChatId());
        if (UserStatusDefiner.isAdmin(message.getFrom().getId(), message.getChatId())) {
            int value = Integer.parseInt(message.getText().split(" ")[1]);
            if (value < 0) {
                value = 0;
            } else if (value > 100) {
                value = 100;
            }
            ChatsAndChances.editChances(message.getChatId(), value);
            sendMessage.text(String.format(SUCCESS_ANSWER, value));
        } else {
            sendMessage.text(NOT_ADMIN_ANSWER);
        }
        return sendMessage.build();
    }
}
