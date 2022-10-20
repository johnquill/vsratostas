package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static stasik.StasikBot.ADMIN_ID;

public class ProcessorsCommandHandler {
    public static SendMessage returnMessage(Message message) {
        if (ADMIN_ID.equals(message.getChatId().toString())) {
            return SendMessage.builder()
                    .chatId(ADMIN_ID)
                    .text("Количество доступных процессоров = " + Runtime.getRuntime().availableProcessors())
                    .build();
        } else return null;
    }
}
