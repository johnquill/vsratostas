package stasik;

import handlers.CommandHandler;
import handlers.MessageHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StasikBot extends TelegramLongPollingBot {

    private static String BOT_NAME;
    public static String BOT_TOKEN;
    public static final String STASIK = "Стасик";

    private static ChatsAndChances chatsAndChances;

    public static Phrases phrases;

    public StasikBot(String bot_name, String bot_token) {
        BOT_NAME = bot_name;
        BOT_TOKEN = bot_token;
        try {
            chatsAndChances = new ChatsAndChances(new File("src/main/resources/chatChances.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            phrases = new Phrases("src/main/resources/phrases.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (Objects.isNull(message)) {
            return;
        }
        if (message.isCommand()) {
            try {
                execute(CommandHandler.handle(message));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                execute(MessageHandler.handle(message));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isEqualsBotName(String str) {
        return !Objects.isNull(str) && !str.isEmpty() && (StasikBot.compareWithBotNames(str));
    }

    @NotNull
    public static boolean compareWithBotNames(@NotNull String str) {
        return str.equals(StasikBot.BOT_NAME) || str.equals(StasikBot.STASIK);
    }
}
