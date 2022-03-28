package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class EmbossingFilter implements IFilter {

    private static final int GRAY_COLOR = 128;


    double[][] kernel;


    public EmbossingFilter() {
        kernel = new double[][]{
                {0, 1, 0},
                {-1, 0, 1},
                {0, -1, 0}
        };
    }

    private int getGrayColor(int color) {
        return ColorUtils.getColorRGB(FilterUtils.roundColor(ColorUtils.parseColorRed(color) + GRAY_COLOR),
                FilterUtils.roundColor(ColorUtils.parseColorGreen(color) + GRAY_COLOR),
                FilterUtils.roundColor(ColorUtils.parseColorBlue(color) + GRAY_COLOR));
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int color = FilterUtils.convolution(oldImage, x, y, kernel);
                newImage.setRGB(x, y, getGrayColor(color));
            }
        }

        return newImage;
    }
}
