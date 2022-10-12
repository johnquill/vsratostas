package handlers;

import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static stasik.StasikBot.BOT_NAME;

public class OnMyChatMemberHandler {

    public static final String HELLO_MESSAGE = "Привет! Чтобы я мог видеть присылаемые фотографии, " +
            "открой мне доступ к сообщениям. Сделать это можно выдав мне права администратора. " +
            "Ознакомиться с моими командами можно, написав /help@" + BOT_NAME + ". Также у меня есть канал и чат, " +
            "в которые вы можете обращаться за помощью или с предложениями (найти их можно в профиле бота). " +
            "Приятного использования!\uD83E\uDD2A";
    public static SendMessage returnMessage(Long id) {
        return SendMessage.builder()
                .text(HELLO_MESSAGE)
                .chatId(id)
                .build();
    }
}
