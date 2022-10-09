package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static stasik.StasikBot.MODER_ID;

public class AddCaptionCommandHandler {

    public static final String ANSWER = "Спасибо, ваша подпись отправлена на модерацию, " +
            "ответа о принятии НЕ последует.";
    public static final String MODER_REQUEST = "Чат: %s\nПользователь: %s\nПодпись: %s\n";
    public static SendMessage returnMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(ANSWER);
        return sendMessage;
    }

    public static SendMessage sendOnModering(Message message) {
        String text = message.getText();
        String caption = text.substring(text.indexOf(" ")+1);
        SendMessage sendMessage = new SendMessage(MODER_ID,
                String.format(MODER_REQUEST, message.getChatId() + "|" + message.getChat().getTitle(),
                        message.getFrom().getId() + "|" + message.getFrom().getUserName(), caption));
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("👍")
                        .callbackData("YES")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("👎")
                        .callbackData("NO")
                        .build()));
        sendMessage.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build());
        return sendMessage;
    }
}
