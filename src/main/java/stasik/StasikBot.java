package stasik;

import handlers.CallbackHandler;
import handlers.CommandHandler;
import handlers.MessageHandler;
import handlers.HelloMessage;
import handlers.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class StasikBot extends TelegramLongPollingBot {

    public static String BOT_NAME;
    public static String BOT_TOKEN;
    public static final HashSet<String> STASIK_NAMES =
            new HashSet<>(Arrays.asList("Стасик", "стасик", "Всратостас", "всратостас"));

    public static final String ADMIN_ID = "927853486";
    public static final String MODER_ID = "-1001514089748";

    public static ChatsAndChances chatsAndChances;

    public static Captions captions;

    public StasikBot(String bot_name, String bot_token) {
        BOT_NAME = bot_name;
        BOT_TOKEN = bot_token;
        try {
            chatsAndChances = new ChatsAndChances("src/main/resources/chatChances.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            captions = new Captions("src/main/resources/captions.properties");
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
        if (update.hasMyChatMember()) {
            if (update.getMyChatMember().getNewChatMember().getStatus().equals("member") &&
                    update.getMyChatMember().getChat().getId().toString().startsWith("-")) {
                try {
                    execute(HelloMessage.returnMessage(update.getMyChatMember().getChat().getId()));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                ChatsAndChances.addChances(update.getMyChatMember().getChat().getId());
            }
        } else if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (!Objects.isNull(message)) {
            try {
                if (message.isCommand()) {
                    handleCommand(message);
                } else {
                    execute(MessageHandler.handle(message));

                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        int num = CallbackHandler.handle(callbackQuery.getMessage(), callbackQuery.getData());
        try {
            execute(CallbackHandler.editMessage(callbackQuery.getMessage(), num));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCommand(Message message) throws TelegramApiException {
        String command = CommandHandler.getCommand(message.getText());
        switch (command) {
            case "/help":
                execute(HelpCommandHandler.returnMessage(message));
                break;
            case "/get_id":
                execute(GetIdCommandHandler.returnMessage(message));
                break;
            case "/add_caption":
                if (AddCaptionCommandHandler.hasCaption(message.getText())) {
                    execute(AddCaptionCommandHandler.sendOnModering(message));
                    execute(AddCaptionCommandHandler.returnMessage(message));
                }
                break;
            case "/download_properties":
                execute(DownloadPropertiesCommandHandler.sendCaptions(message));
                execute(DownloadPropertiesCommandHandler.sendChatChances(message));
                break;
            case "/processors":
                execute(ProcessorsCommandHandler.returnMessage(message));
                break;
            case "/set_chance":
                execute(SetChanceCommandHandler.returnMessage(message));
                break;
            case "/start":
                execute(HelloMessage.returnMessage(message.getChatId()));
                break;
        }
    }

    public static boolean isEqualsBotName(String str) {
        return !Objects.isNull(str) && !str.isEmpty() && (StasikBot.compareWithBotNames(str));
    }

    @NotNull
    public static boolean compareWithBotNames(@NotNull String str) {
        return STASIK_NAMES.contains(str);
    }
}
