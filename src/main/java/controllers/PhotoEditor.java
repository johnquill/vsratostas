package controllers;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PhotoEditor {
    public static SendPhoto editPhoto(Message message) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId());

        PhotoSize photo = message.getPhoto().get(3);
        File file;
        try {
            file = FileDownloader.downloadById(photo.getFileId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage bufferedImage;
        try {
             bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bufferedImage = ImageEditor.addPhrase(bufferedImage, photo);
        try {
            ImageIO.write(bufferedImage, "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sendPhoto.setPhoto(new InputFile(file));

        return sendPhoto;
    }
}
