package ru.nsu.ccfit.trubitsyna.utils;

import java.awt.image.BufferedImage;

public class FilterUtils {
    public final static int MIN_COLOR = 0;
    public final static int MAX_COLOR = 255;

    public static int roundColor(double color) {
        return (int) Math.max(Math.min(color, MAX_COLOR), MIN_COLOR);
    }

    public static void fillPalette(int[] palette, int step, double size) {
        int colorValue = 0;
        for (int i = 0; i < size; ++i) {
            palette[i] = Math.min(colorValue, MAX_COLOR);
            colorValue += step;
        }
    }
    public static int nearestPaletteColor(double color, int[] palette) {
        double minDist = MAX_COLOR;
        int position = 0;
        for (int i = 0; i < palette.length; ++i) {
            if (Math.abs(palette[i] - color) < minDist) {
                minDist = Math.abs(palette[i] - color);
                position = i;
            }
        }
        return palette[position];
    }

    public static int convolution(BufferedImage image, int currX, int currY, double[][] kernel) {
        double resultR = 0;
        double resultG = 0;
        double resultB = 0;

        int i = 0;
        int j = 0;

        for (int y = currY - 1; y <= currY + 1; ++y) {
            for (int x = currX - 1; x <= currX + 1; ++x) {
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                    resultR += ColorUtils.parseColorRed(image.getRGB(x, y)) * kernel[i][j];
                    resultG += ColorUtils.parseColorGreen(image.getRGB(x, y)) * kernel[i][j];
                    resultB += ColorUtils.parseColorBlue(image.getRGB(x, y)) * kernel[i][j];

                }
                j = (j + 1) % kernel[0].length;

            }
            i = (i + 1) % kernel[0].length;
        }

        resultR = FilterUtils.roundColor(resultR);
        resultG = FilterUtils.roundColor(resultG);
        resultB = FilterUtils.roundColor(resultB);

        return ColorUtils.getColorRGB((int)resultR, (int)resultG, (int)resultB);
    }


}
