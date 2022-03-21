package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.ColorPuller;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {
    private static final double COEF_RED =  0.299;
    private static final double COEF_GREEN =  0.587;
    private static final double COEF_BLUE =  0.114;

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage,  double... params) {
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x,y);
                int red = (int) Math.round(ColorPuller.parseColorRed(color) * COEF_RED);
                int green = (int) Math.round(ColorPuller.parseColorGreen(color) * COEF_GREEN);
                int blue =(int) Math.round(ColorPuller.parseColorBlue(color) * COEF_BLUE);
                int newColor = red + blue + green;
                newImage.setRGB(x, y, ColorPuller.getColorRGB(newColor, newColor, newColor));
            }
        }

        return null;
    }
}
