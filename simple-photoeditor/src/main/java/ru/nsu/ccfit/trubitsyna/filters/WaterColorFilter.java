package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WaterColorFilter implements IFilter {

    private final static int DEFAULT_SIZE = 5;

    private final double[][] sharpKernel;

    public WaterColorFilter() {
        sharpKernel = new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
    }

    private int medianBlur(BufferedImage image, int currX, int currY) {
        int lastNotFilledElem = 0;
        int[] arrayR = new int[DEFAULT_SIZE * DEFAULT_SIZE];
        int[] arrayG = new int[DEFAULT_SIZE * DEFAULT_SIZE];
        int[] arrayB = new int[DEFAULT_SIZE * DEFAULT_SIZE];
        for (int y = currY - 2; y <= currY + 2; ++y) {
            for (int x = currX - 2; x <= currX + 2; ++x) {
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                    arrayR[lastNotFilledElem] = ColorUtils.parseColorRed(image.getRGB(x, y));
                    arrayG[lastNotFilledElem] = ColorUtils.parseColorGreen(image.getRGB(x, y));
                    arrayB[lastNotFilledElem] = ColorUtils.parseColorBlue(image.getRGB(x, y));
                    lastNotFilledElem++;
                }
            }
        }
        Arrays.sort(arrayR);
        Arrays.sort(arrayG);
        Arrays.sort(arrayB);
        return ColorUtils.getColorRGB(arrayR[DEFAULT_SIZE * DEFAULT_SIZE / 2], arrayG[DEFAULT_SIZE * DEFAULT_SIZE / 2], arrayB[DEFAULT_SIZE * DEFAULT_SIZE / 2]);
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        BufferedImage processedImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                processedImage.setRGB(x, y, medianBlur(oldImage, x, y));
            }
        }

        BufferedImage newProcessedImage = ImagePanel.copyImage(processedImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newProcessedImage.setRGB(x, y, medianBlur(processedImage, x, y));
            }
        }

        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, FilterUtils.convolution(newProcessedImage, x, y, sharpKernel));
            }
        }

        return newImage;
    }
}
