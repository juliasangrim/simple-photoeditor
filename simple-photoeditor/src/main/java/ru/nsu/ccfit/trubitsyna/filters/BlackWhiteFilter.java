package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {
    private static final double COEF_RED = 0.299;
    private static final double COEF_GREEN = 0.587;
    private static final double COEF_BLUE = 0.114;

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = oldImage.getRGB(x, y);
                int red = (int) Math.round(ColorUtils.parseColorRed(color) * COEF_RED);
                int green = (int) Math.round(ColorUtils.parseColorGreen(color) * COEF_GREEN);
                int blue = (int) Math.round(ColorUtils.parseColorBlue(color) * COEF_BLUE);
                int newColor = red + blue + green;

                newImage.setRGB(x, y, ColorUtils.getColorRGB(newColor, newColor, newColor));
            }
        }

        return newImage;
    }
}
