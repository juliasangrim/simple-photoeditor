package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.ColorPuller;

import java.awt.image.BufferedImage;

public class InversionFilter implements IFilter{


    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage,  double... params) {
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x,y);
                int red = 255 - ColorPuller.parseColorRed(color);
                int green = 255 - ColorPuller.parseColorGreen(color);
                int blue = 255 - ColorPuller.parseColorBlue(color);
                newImage.setRGB(x, y, ColorPuller.getColorRGB(red, green, blue));
            }
        }

        return null;
    }
}
