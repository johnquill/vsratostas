package controllers;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.awt.*;
import java.awt.image.BufferedImage;

import static stasik.StasikBot.phrases;

public class ImageEditor {

    public static BufferedImage addPhrase(BufferedImage image, PhotoSize photo) {
        Graphics graphics = image.getGraphics();
        String phrase = phrases.getRandomPhrase();
        int fontSize = calcFontSize(photo.getWidth(), phrase.length());
        int x = calcX(photo.getWidth(), phrase.length(), fontSize);
        int y = calcY(photo.getHeight(), fontSize);
        graphics.setFont(new Font("Inpact", Font.BOLD, fontSize));
        graphics.drawString(phrase, x, y);
        return image;
    }

    private static int calcX(Integer width, Integer length, Integer fontSize) {
        return (int) ((width - length * fontSize/2.2) / 2);
    }

    private static int calcY(Integer height, Integer fontSize) {
        return height - fontSize;
    }

    private static int calcFontSize(Integer width, Integer length) {
        return (int) (width/length * (double) 4/3);
    }
}
