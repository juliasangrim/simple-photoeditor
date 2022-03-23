package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.ColorPuller;

import java.awt.image.BufferedImage;

public class SharpnessFilter implements IFilter{
    int[][] kernel;


    public SharpnessFilter() {
        kernel = new int[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
    }

    public static int sharpness(BufferedImage image, int currX, int currY, int[][] kernel) {
        int resultR = 0;
        int resultG = 0;
        int resultB = 0;

        int i = 0;
        int j = 0;
        for (int y = currY - 1; y <= currY + 1; ++y) {
            for (int x = currX - 1; x <= currX + 1; ++x) {
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                    resultR += ColorPuller.parseColorRed(image.getRGB(x, y)) * kernel[i][j];
                    resultG += ColorPuller.parseColorGreen(image.getRGB(x, y)) * kernel[i][j];
                    resultB += ColorPuller.parseColorBlue(image.getRGB(x, y)) * kernel[i][j];

                }
                j = (j + 1) % kernel[0].length;
            }
            i = (i + 1) % kernel[0].length;
        }

        resultR = Math.min(Math.max(resultR, 0), 255);;
        resultG = Math.min(Math.max(resultG, 0), 255);
        resultB = Math.min(Math.max(resultB, 0) , 255);

        return ColorPuller.getColorRGB(resultR, resultG, resultB);
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage, double... params) {

        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, sharpness(oldImage, x, y, kernel));
            }
        }

        return null;
    }
}
