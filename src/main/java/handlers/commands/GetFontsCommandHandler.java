package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.awt.*;

import static stasik.StasikBot.ADMIN_ID;

public class GetFontsCommandHandler {
    // TODO: 30.10.2022 не работает по причине длинного сообщения

    public static SendMessage returnMessage(Message message) {
        if (ADMIN_ID.equals(message.getChatId().toString())) {
            GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
            StringBuilder stringBuilder = new StringBuilder();
            for (Font el : e.getAllFonts()) {
                stringBuilder.append(el.getName()).append("\n");
            }
            return SendMessage.builder()
                    .chatId(ADMIN_ID)
                    .text(stringBuilder.toString())
                    .build();
        } else return null;
    }
}
