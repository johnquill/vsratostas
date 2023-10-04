package stasik;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StasikBot extends TelegramLongPollingBot {

    public static String BOT_NAME;

    public static String BOT_TOKEN;

    public static final HashSet<String> STASIK_NAMES = new HashSet<>(Arrays.asList("стасик", "всратостас", "славик",
            "всратослав", "стас", "сосик", "стасян", "стася"));

    public static final String ADMIN_ID = "927853486";
    public static final String MODER_ID = "-1001514089748";

    public static ChatsAndChances chatsAndChances;
    public static Captions captions;
    public Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();
    public Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();

    public StasikBot(String bot_name, String bot_token) {
        BOT_NAME = bot_name;
        BOT_TOKEN = bot_token;
        try {
            chatsAndChances = new ChatsAndChances("src/main/resources/chatChances.properties");
            captions = new Captions("src/main/resources/captions.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessageSender messageSender = new MessageSender(this);
        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("Sender");
        sender.start();
        MessageReceiver messageReceiver = new MessageReceiver(this);
        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("Receiver");
        receiver.start();
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
        receiveQueue.add(update);
    }

    public void addToSend(Object object) {
        if (!Objects.isNull(object)) {
            sendQueue.add(object);
        }
    }

    public static boolean isEqualsBotName(String str) {
        return !Objects.isNull(str) && !str.isEmpty() && (StasikBot.compareWithBotNames(str.toLowerCase()));
    }

    @NotNull
    public static boolean compareWithBotNames(@NotNull String str) {
        return STASIK_NAMES.contains(str);
    }

}
