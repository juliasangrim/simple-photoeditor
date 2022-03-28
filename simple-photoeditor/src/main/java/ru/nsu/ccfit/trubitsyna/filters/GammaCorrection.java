package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class GammaCorrection implements IFilter {

    private final static int MAX_COLOR = 255;


    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        double gamma = params[0];
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x, y);
                int red = (int) (Math.pow((double) ColorUtils.parseColorRed(color) / MAX_COLOR, gamma) * MAX_COLOR);
                int green = (int) (Math.pow((double) ColorUtils.parseColorGreen(color) / MAX_COLOR, gamma) * MAX_COLOR);
                int blue = (int) (Math.pow((double) ColorUtils.parseColorBlue(color) / MAX_COLOR, gamma) * MAX_COLOR);

                newImage.setRGB(x, y, ColorUtils.getColorRGB(red, green, blue));
            }
        }

        return newImage;
    }
}
