package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommandHandler {

    public static final String HELP = "Пришли фото с подписью \"Cтасик\", чтобы я добавил на него смешной текст " +
            "(или можно просто ответить сообщением с текстом \"Стасик\" на сообщение с фотографией).\n\n" +
            "/add_caption новая подпись - предложить подпись \"новая подпись\"\n\n" +
            "/set_chance 50 - установить шанс случайного срабатывания Стасика на всланное фото равным 50%. " +
            "Изначально этот шанс установлен на 10%";

    public static SendMessage returnMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(HELP);
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }
}
