package handlers;

import handlers.commands.HelloMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import stasik.ChatsAndChances;
import stasik.StasikBot;

import java.util.Objects;

public class UpdateHandler {

    StasikBot bot;
    CallbackHandler callbackHandler;
    CommandHandler commandHandler;
    MessageHandler messageHandler;

    public UpdateHandler(StasikBot stasikBot) {
        bot = stasikBot;
        callbackHandler = new CallbackHandler(bot);
        commandHandler = new CommandHandler(bot);
        messageHandler = new MessageHandler(bot);
    }

    public void handle(Update update) {
        Message message = update.getMessage();
        if (update.hasMyChatMember()) {
            handleNewChat(update);
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handleCallback(update.getCallbackQuery());
        } else if (!Objects.isNull(message)) {
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        if (message.isCommand()) {
            commandHandler.handleCommand(message);
        } else {
            messageHandler.handle(message);
        }
    }

    private void handleNewChat(Update update) {
        if (update.getMyChatMember().getNewChatMember().getStatus().equals("member") &&
                update.getMyChatMember().getChat().getId().toString().startsWith("-")) {
            bot.addToSend(HelloMessage.returnMessage(update.getMyChatMember().getChat().getId()));
            ChatsAndChances.addChances(update.getMyChatMember().getChat().getId());
        }
    }
}
