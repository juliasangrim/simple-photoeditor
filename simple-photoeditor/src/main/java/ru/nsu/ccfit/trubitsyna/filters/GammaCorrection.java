package ru.nsu.ccfit.trubitsyna.filters;

import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.ColorPuller;

import java.awt.image.BufferedImage;

public class GammaCorrection implements IFilter{

    private final static int MAX_COLOR = 255;

    @Setter
    private double gamma = 0.6;

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage, double... params) {
        gamma = params[0];
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x,y);
                int red = (int) (Math.pow((double)ColorPuller.parseColorRed(color) / MAX_COLOR, gamma) * MAX_COLOR);
                int green = (int) (Math.pow((double)ColorPuller.parseColorGreen(color) / MAX_COLOR, gamma) * MAX_COLOR);
                int blue =(int) (Math.pow((double)ColorPuller.parseColorBlue(color) / MAX_COLOR, gamma) * MAX_COLOR);
                newImage.setRGB(x, y, ColorPuller.getColorRGB(red, green, blue));
            }
        }

        return null;
    }
}
