package handlers;

import controllers.PhotoEditor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import stasik.StasikBot;

import java.util.List;

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
            return null;
        }
    }
}
