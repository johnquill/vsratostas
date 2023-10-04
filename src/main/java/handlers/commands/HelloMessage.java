package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static stasik.StasikBot.BOT_NAME;

public class HelloMessage {

    public static final String HELLO_MESSAGE = "Привет! Ознакомиться с моими командами можно, написав " +
            "/help@" + BOT_NAME + ". Также у меня есть канал и чат, в которые вы можете обращаться " +
            "за помощью или с предложениями (найти их можно в профиле бота).\n Приятного использования!\uD83E\uDD2A";
    public static SendMessage returnMessage(Long id) {
        return SendMessage.builder()
                .text(HELLO_MESSAGE)
                .chatId(id)
                .build();
    }
}
