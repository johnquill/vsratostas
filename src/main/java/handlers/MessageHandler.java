package handlers;

import controllers.PhotoEditor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.CaptionPlacement;
import stasik.StasikBot;

import static stasik.StasikBot.chatsAndChances;

public class MessageHandler {

    StasikBot bot;

    public MessageHandler(StasikBot bot) {
        this.bot = bot;
    }

    public void handle(Message message) {
        if (message.hasPhoto()) {
            bot.addToSend(messageWithPhotoHandler(message));
        } else if (message.isReply()) {
            bot.addToSend(replyMessageHandler(message));
        }
    }

    private static SendPhoto replyMessageHandler(Message message) {
        Message replied = message.getReplyToMessage();
        if (message.getText() != null) {
            String[] textWords = message.getText().split(" ");
            if (replied.hasPhoto() && StasikBot.isEqualsBotName(textWords[0])) {
                CaptionPlacement placement = CaptionPlacement.DOWN;
                if (textWords.length > 1 && textWords[1].toLowerCase().matches("(.+)?верх(.+)?")) {
                    placement = CaptionPlacement.UP;
                }
                return PhotoEditor.editPhoto(message.getReplyToMessage(), placement);
            }
        }
        return null;
    }

    private static SendPhoto messageWithPhotoHandler(Message message) {
        if (message.getChatId() > 0) {
            return PhotoEditor.editPhoto(message, CaptionPlacement.DOWN);
        }
        String[] captionWords = message.getCaption().split(" ");
        if (StasikBot.isEqualsBotName(captionWords[0])) {
            CaptionPlacement placement = CaptionPlacement.DOWN;
            if (captionWords.length > 1 && captionWords[1].toLowerCase().matches("(.+)?верх(.+)?")) {
                placement = CaptionPlacement.UP;
            }
            return PhotoEditor.editPhoto(message, placement);
        } else {
            int chance = chatsAndChances.getChance(message.getChatId());
            if ((int) (Math.random() * 99) < chance) {
                return PhotoEditor.editPhoto(message, CaptionPlacement.DOWN);
            } else {
                return null;
            }
        }
    }
}
