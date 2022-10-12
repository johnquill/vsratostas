package handlers;

import controllers.PhotoEditor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.ChatsAndChances;
import stasik.StasikBot;


public class MessageHandler {
    public static SendPhoto handle(Message message) {
        if (message.hasPhoto()) {
            return messageWithPhotoHandler(message);
        } else if (message.isReply()) {
            return replyMessageHandler(message);
        } else {
            return null;
        }
    }

    private static SendPhoto replyMessageHandler(Message message) {
        Message replied = message.getReplyToMessage();
        if (replied.hasPhoto() && StasikBot.isEqualsBotName(message.getText())) {
            return PhotoEditor.editPhoto(message.getReplyToMessage());
        }
        return null;
    }

    private static SendPhoto messageWithPhotoHandler(Message message) {
        if (StasikBot.isEqualsBotName(message.getCaption())) {
            return PhotoEditor.editPhoto(message);
        } else {
            int chance = ChatsAndChances.getChance(message.getChatId());
            if ((int) (Math.random() * 99) < chance) {
                return PhotoEditor.editPhoto(message);
            } else {
                return null;
            }
        }
    }
}
