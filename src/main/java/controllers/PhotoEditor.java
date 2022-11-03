package controllers;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import stasik.CaptionPlacement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PhotoEditor {
    public static SendPhoto editPhoto(Message message, CaptionPlacement placement) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId());

        PhotoSize photo = message.getPhoto().get(message.getPhoto().size()-1);
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

        ImageEditor.addCaption(bufferedImage, placement);
        try {
            ImageIO.write(bufferedImage, "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sendPhoto.setPhoto(new InputFile(file));

        return sendPhoto;
    }
}
