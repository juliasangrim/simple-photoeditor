package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class InversionFilter implements IFilter {


    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x, y);
                int red = 255 - ColorUtils.parseColorRed(color);
                int green = 255 - ColorUtils.parseColorGreen(color);
                int blue = 255 - ColorUtils.parseColorBlue(color);
                newImage.setRGB(x, y, ColorUtils.getColorRGB(red, green, blue));
            }
        }

        return newImage;
    }
}
