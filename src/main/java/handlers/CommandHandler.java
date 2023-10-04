package handlers;

import handlers.commands.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.StasikBot;

public class CommandHandler {

    StasikBot bot;

    public CommandHandler(StasikBot bot) {
        this.bot = bot;
    }

    public void handleCommand(Message message) {
        String command = getCommand(message.getText());
        switch (command) {
            case "/help":
                bot.addToSend(HelpCommandHandler.returnMessage(message));
                break;
            case "/get_id":
                bot.addToSend(GetIdCommandHandler.returnMessage(message));
                break;
            case "/add_caption":
                if (AddCaptionCommandHandler.hasCaption(message.getText())) {
                    bot.addToSend(AddCaptionCommandHandler.sendOnModering(message));
                    bot.addToSend(AddCaptionCommandHandler.returnMessage(message));
                }
                break;
            case "/download_properties":
                bot.addToSend(DownloadPropertiesCommandHandler.sendCaptions(message));
                bot.addToSend(DownloadPropertiesCommandHandler.sendChatChances(message));
                break;
            case "/processors":
                bot.addToSend(ProcessorsCommandHandler.returnMessage(message));
            case "/getFonts":
                bot.addToSend(GetFontsCommandHandler.returnMessage(message));
                break;
            case "/set_chance":
                bot.addToSend(SetChanceCommandHandler.returnMessage(message));
                break;
            case "/start":
                bot.addToSend(HelloMessage.returnMessage(message.getChatId()));
                break;
        }
    }

    public String getCommand(String text) {
        String[] arr = text.split("@");
        if (arr.length > 1) {
            String botName = arr[1].split(" ")[0];
            if (!botName.equals(StasikBot.BOT_NAME)) {
                return "";
            }
        }
        return arr[0].split(" ")[0];
    }
}
