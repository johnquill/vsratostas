package handlers;

import controllers.PhotoEditor;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.CaptionPlacement;
import stasik.StasikBot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static stasik.StasikBot.chatsAndChances;


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
        String[] textWords = message.getText().split(" ");
        if (replied.hasPhoto() && StasikBot.isEqualsBotName(textWords[0])) {
            CaptionPlacement placement = CaptionPlacement.DOWN;
            if (textWords[1].matches("(.+)?верх(.+)?")) {
                placement = CaptionPlacement.UP;
            }
            return PhotoEditor.editPhoto(message.getReplyToMessage(), placement);
        }
        return null;
    }

    private static SendPhoto messageWithPhotoHandler(Message message) {
        String[] captionWords = message.getCaption().split(" ");
        if (StasikBot.isEqualsBotName(captionWords[0])) {
            CaptionPlacement placement = CaptionPlacement.DOWN;
            Pattern pattern = Pattern.compile("(.+)?верх(.+)?");
            Matcher matcher = pattern.matcher(captionWords[1].toLowerCase());
            if (matcher.find()) {
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
