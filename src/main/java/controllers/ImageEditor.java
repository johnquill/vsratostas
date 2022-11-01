package controllers;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import static stasik.StasikBot.captions;

public class ImageEditor {

    public static BufferedImage addCaption(BufferedImage image) {
        String caption = captions.getRandomCaption();
        Font font = createFontToFit(new Font("Lobster Regular", Font.PLAIN, 1000), caption, image);

        AttributedString attributedText = new AttributedString(caption);
        attributedText.addAttribute(TextAttribute.FONT, font);

        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        FontMetrics metrics = g2d.getFontMetrics(font);
        int positionX = (image.getWidth() - metrics.stringWidth(caption)) / 2;
        int positionY = (int) ((image.getHeight() * 0.98) - metrics.getHeight() / 2);

        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        g2d.drawString(attributedText.getIterator(),
                (int) (positionX + metrics.getHeight() * 0.03),
                (int) (positionY + metrics.getHeight() * 0.03));

        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
        g2d.drawString(attributedText.getIterator(), positionX, positionY);
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
            double heightBasedFontSize = (baseFont.getSize2D() * image.getHeight() / 15) / expectedHeight;

            double newFontSize = Math.min(widthBasedFontSize, heightBasedFontSize);
            newFont = baseFont.deriveFont(baseFont.getStyle(), (float)newFontSize);
        }
        return newFont;
    }
}
