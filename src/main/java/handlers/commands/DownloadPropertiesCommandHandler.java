package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.Captions;
import stasik.ChatsAndChances;
import stasik.WorkSpace;

import java.io.File;

public class DownloadPropertiesCommandHandler {
    public static SendDocument sendCaptions(Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId());
        if (WorkSpace.isAdmin(message.getChatId().toString())) {
            Captions.saveCaptions();
            sendDocument.setDocument(new InputFile(new File("src/main/resources/captions.properties")));
        }
        return sendDocument;
    }

    public static SendDocument sendChatChances(Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId());
        if (WorkSpace.isAdmin(message.getChatId().toString())) {
            ChatsAndChances.saveChances();
            sendDocument.setDocument(new InputFile(new File("src/main/resources/chatChances.properties")));
        }
        return sendDocument;
    }
}
