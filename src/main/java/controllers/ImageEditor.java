package controllers;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import static java.lang.Math.min;
import static stasik.StasikBot.captions;

public class ImageEditor {

    public static BufferedImage addCaption(BufferedImage image, PhotoSize photo) {
        Graphics graphics = image.getGraphics();
        String caption = captions.getRandomCaption();
        /*int fontSize = calcFontSize(photo.getWidth(), photo.getHeight(), caption.length());
        int x = calcX(photo.getWidth(), caption.length(), fontSize);
        int y = calcY(photo.getHeight(), fontSize);
        graphics.setFont(new Font("Lobster Regular", Font.PLAIN, fontSize));
        graphics.drawString(caption, x, y);*/

        Font font = createFontToFit(new Font("Lobster Regular", Font.PLAIN, 80), caption, image);

        AttributedString attributedText = new AttributedString(caption);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);

        Graphics g = image.getGraphics();

        FontMetrics metrics = g.getFontMetrics(font);
        int positionX = (image.getWidth() - metrics.stringWidth(caption)) / 2;
        int positionY = (int) ((image.getHeight() - metrics.getHeight() * 1.7) + metrics.getAscent());

        g.drawString(attributedText.getIterator(), positionX, positionY);

        return image;
    }

    public static Font createFontToFit(Font baseFont, String text, BufferedImage image) {
        Font newFont = baseFont;

        FontMetrics ruler = image.getGraphics().getFontMetrics(baseFont);
        GlyphVector vector = baseFont.createGlyphVector(ruler.getFontRenderContext(), text);

        Shape outline = vector.getOutline(0, 0);

        double expectedWidth = outline.getBounds().getWidth();
        double expectedHeight = outline.getBounds().getHeight();

        boolean textFits = image.getWidth() >= expectedWidth && image.getHeight() >= expectedHeight;

        if(!textFits) {
            double widthBasedFontSize = (baseFont.getSize2D() * image.getWidth() * 0.9) / expectedWidth;
            double heightBasedFontSize = (baseFont.getSize2D() * image.getHeight()) / expectedHeight;

            double newFontSize = Math.min(widthBasedFontSize, heightBasedFontSize);
            newFont = baseFont.deriveFont(baseFont.getStyle(), (float)newFontSize);
        }
        return newFont;
    }

    private static int calcX(Integer width, Integer captionLength, Integer fontSize) {
        return (int) ((width - fontSize/2 * captionLength * (double) (4/3)) / 0.84 / 2) / 2;
    }

    private static int calcY(Integer height, Integer fontSize) {
        return height - fontSize;
    }

    private static int calcFontSize(Integer width, Integer height, Integer captionLength) {
        return min((int) ((width/captionLength/((double) 4/3))*2*0.84),
                (int) (height/10/((double) 4/3)));
    }
}
