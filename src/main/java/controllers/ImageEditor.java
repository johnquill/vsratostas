package controllers;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.min;
import static stasik.StasikBot.captions;

public class ImageEditor {

    public static BufferedImage addCaption(BufferedImage image, PhotoSize photo) {
        Graphics graphics = image.getGraphics();
        String caption = captions.getRandomCaption();
        int fontSize = calcFontSize(photo.getWidth(), photo.getHeight(), caption.length());
        int x = calcX(photo.getWidth(), caption.length(), fontSize);
        int y = calcY(photo.getHeight(), fontSize);
        graphics.setFont(new Font("Lobster", Font.BOLD, fontSize));
        graphics.drawString(caption, x, y);
        return image;
    }

    private static int calcX(Integer width, Integer captionLength, Integer fontSize) {
        return (width - fontSize/2 * captionLength) / 2;
    }

    private static int calcY(Integer height, Integer fontSize) {
        return height - fontSize;
    }

    private static int calcFontSize(Integer width, Integer height, Integer captionLength) {
        return min((int) ((width/captionLength/((double) 4/3))*2*0.84),
                (int) (height/10/((double) 4/3)));
    }
}
