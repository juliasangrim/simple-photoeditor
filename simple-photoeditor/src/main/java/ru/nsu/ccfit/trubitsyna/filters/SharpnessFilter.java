package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class SharpnessFilter implements IFilter {
    double[][] kernel;


    public SharpnessFilter() {
        kernel = new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
    }


    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, FilterUtils.convolution(oldImage, x, y, kernel));
            }
        }

        return newImage;
    }
}
