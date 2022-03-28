package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class FloydDithering implements IFilter {
    private final static int MAX_COLOR = 255;
    private final static double COLOR_AMOUNT = 256;

    private int[] redPalette;
    private int[] greenPalette;
    private int[] bluePalette;


    private void fillPalette(int[] palette, int step, double size) {
        int colorValue = 0;
        for (int i = 0; i < size; ++i) {
            palette[i] = Math.min(colorValue, MAX_COLOR);
            colorValue += step;
        }
    }


    private void createPalette(double rSIze, double gSize, double bSize) {
        redPalette = new int[(int) rSIze];
        greenPalette = new int[(int) gSize];
        bluePalette = new int[(int) bSize];
        int step = (int) (COLOR_AMOUNT / (rSIze - 1));
        fillPalette(redPalette, step, rSIze);

        step = (int) (COLOR_AMOUNT / (gSize - 1));
        fillPalette(greenPalette, step, gSize);

        step = (int) (COLOR_AMOUNT / (bSize - 1));
        fillPalette(bluePalette, step, bSize);

    }


    private int getColor(int pixelValue, double coef, int errR, int errG, int errB) {
        return ColorUtils.getColorRGB(
                FilterUtils.roundColor(ColorUtils.parseColorRed(pixelValue) + coef * errR),
                FilterUtils.roundColor(ColorUtils.parseColorGreen(pixelValue) + coef * errG),
                FilterUtils.roundColor(ColorUtils.parseColorBlue(pixelValue) + coef * errB)
        );
    }

    private void floydDithering(BufferedImage newImage) {
        for (int y = 0; y < newImage.getHeight(); ++y) {
            for (int x = 0; x < newImage.getWidth(); ++x) {
                int oldR = ColorUtils.parseColorRed(newImage.getRGB(x, y));
                int oldG = ColorUtils.parseColorGreen(newImage.getRGB(x, y));
                int oldB = ColorUtils.parseColorBlue(newImage.getRGB(x, y));

                int newR = FilterUtils.nearestPaletteColor(oldR, redPalette);
                int newG = FilterUtils.nearestPaletteColor(oldG, greenPalette);
                int newB = FilterUtils.nearestPaletteColor(oldB, bluePalette);

                newImage.setRGB(x, y, ColorUtils.getColorRGB(newR, newG, newB));

                int errR = oldR - newR;
                int errG = oldG - newG;
                int errB = oldB - newB;
                int pixelsValue;

                if (x < newImage.getWidth() - 1) {
                    pixelsValue = newImage.getRGB(x + 1, y);
                    newImage.setRGB(x + 1, y, getColor(pixelsValue, 7.0 / 16, errR, errG, errB));
                }
                if (x > 0 && y < newImage.getHeight() - 1) {
                    pixelsValue = newImage.getRGB(x - 1, y + 1);
                    newImage.setRGB(x - 1, y + 1, getColor(pixelsValue, 3.0 / 16, errR, errG, errB));
                }
                if (y < newImage.getHeight() - 1) {
                    pixelsValue = newImage.getRGB(x, y + 1);
                    newImage.setRGB(x, y + 1, getColor(pixelsValue, 5.0 / 16, errR, errG, errB));

                }
                if (x < newImage.getWidth() - 1 && y < newImage.getHeight() - 1) {
                    pixelsValue = newImage.getRGB(x + 1, y + 1);
                    newImage.setRGB(x + 1, y + 1, getColor(pixelsValue, 1.0 / 16, errR, errG, errB));
                }
            }
        }

    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        double rSize = params[0];
        double gSize = params[1];
        double bSize = params[2];

        createPalette(rSize, gSize, bSize);
        floydDithering(newImage);

        return newImage;
    }
}
