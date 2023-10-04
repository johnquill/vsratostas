package stasik;

import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender implements Runnable {

    StasikBot bot;

    public MessageSender(StasikBot stasikBot) {
        bot = stasikBot;
    }

    @Override
    public void run() {
        while (true) {
            for (Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) {
                try {
                    send(object);
                } catch (TelegramApiException e) {
                    return;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(Object object) throws TelegramApiException {
        if (object instanceof SendMessage) {
            bot.execute((SendMessage) object);
        } else if (object instanceof SendPhoto) {
            bot.execute((SendPhoto) object);
        } else if (object instanceof SendDocument) {
            bot.execute((SendDocument) object);
        } else if (object instanceof EditMessageText) {
            bot.execute((EditMessageText) object);
        } else {
            bot.execute(SendDice.builder().chatId(StasikBot.ADMIN_ID).build());
        }
    }
}
