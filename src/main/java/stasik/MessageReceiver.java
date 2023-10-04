package stasik;

import handlers.UpdateHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageReceiver implements Runnable {

    StasikBot bot;
    UpdateHandler updateHandler;

    public MessageReceiver(StasikBot stasikBot) {
        bot = stasikBot;
        updateHandler = new UpdateHandler(stasikBot);
    }

    @Override
    public void run() {
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                handle(object);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Object object) {
        if (object instanceof Update) {
            updateHandler.handle((Update) object);
        }
    }

}
