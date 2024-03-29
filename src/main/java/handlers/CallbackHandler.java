package handlers;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import stasik.Captions;
import stasik.StasikBot;

import java.util.ArrayList;
import java.util.List;

public class CallbackHandler {

    StasikBot bot;

    public CallbackHandler(StasikBot bot) {
        this.bot = bot;
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        int num = handle(callbackQuery.getMessage(), callbackQuery.getData());
        bot.addToSend(editMessage(callbackQuery.getMessage(), num));
    }

    public int handle(Message message, String data) {
        if (data.equals("YES")) {
            String[] strings = message.getText().split(": ");
            int num = Captions.addCaption(strings[strings.length-1]);
            return num;
        }
        return -1;
    }

    public EditMessageText editMessage(Message message, int num) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        String text = message.getText();
        if (num == -1) {
            text += "\n❌Не принято";
        } else {
            text += "\n✅Принято, номер = " + num;
        }
        return EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                .build();
    }
}
