package handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import stasik.Captions;
import stasik.WorkSpace;

import java.io.File;

public class DownloadCaptionsCommandHandler {
    public static SendDocument returnMessage(Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId());
        if (WorkSpace.isAdmin(message.getChatId().toString())) {
            Captions.saveCaptions();
            sendDocument.setDocument(new InputFile(new File("src/main/resources/captions.properties")));
        }
        return sendDocument;
    }
}
