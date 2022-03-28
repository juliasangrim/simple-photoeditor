package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;


public class OrderDithering implements IFilter {
    private final static double COLOR_AMOUNT = 256;


    private final double[][] beginKernel = {
            {0, 2},
            {3, 1}
    };

    private final int[] possibleSize = {2, 4, 8, 16};

    private int[] redPalette;
    private int[] greenPalette;
    private int[] bluePalette;

    private double[][] thresholdMap;


    private void createPalette(double rSIze, double gSize, double bSize) {
        redPalette = new int[(int) rSIze];
        greenPalette = new int[(int) gSize];
        bluePalette = new int[(int) bSize];

        int step = (int) (COLOR_AMOUNT / (rSIze - 1));
        FilterUtils.fillPalette(redPalette, step, rSIze);

        step = (int) (COLOR_AMOUNT / (gSize - 1));
        FilterUtils.fillPalette(greenPalette, step, gSize);

        step = (int) (COLOR_AMOUNT / (bSize - 1));
        FilterUtils.fillPalette(bluePalette, step, bSize);

    }

    private int countSize(double rSize, double gSize, double bSize) {
        double imgSizeR = COLOR_AMOUNT / rSize;
        double imgSizeG = COLOR_AMOUNT / gSize;
        double imgSizeB = COLOR_AMOUNT / bSize;
        double maxSize = Math.max(imgSizeR, imgSizeG);
        maxSize = Math.max(maxSize, imgSizeB);

        int finalSize = 0;
        for (int size : possibleSize) {
            System.out.println(size * size);
            if (maxSize <= size * size) {
                finalSize = size;
                break;
            }
        }
        return finalSize;
    }

    private double[][] createThresholdMap(int newSize) {
        double[][] oldKernel;
        if (newSize == beginKernel[0].length) {
            return beginKernel;
        }
        oldKernel = createThresholdMap(newSize / 2);
        double[][] newKernel = new double[oldKernel[0].length * 2][oldKernel[0].length * 2];

        for (int y = 0; y < oldKernel[0].length; ++y) {
            for (int x = 0; x < oldKernel[0].length; ++x) {
                newKernel[y][x] = 4 * oldKernel[y % oldKernel[0].length][x % oldKernel[0].length];
            }
        }

        for (int y = 0; y < oldKernel[0].length; ++y) {
            for (int x = oldKernel[0].length; x < 2 * oldKernel[0].length; ++x) {
                newKernel[y][x] = 4 * oldKernel[y % oldKernel[0].length][x % oldKernel[0].length] + 2;
            }
        }
        for (int y = oldKernel[0].length; y < 2 * oldKernel[0].length; ++y) {
            for (int x = 0; x < oldKernel[0].length; ++x) {
                newKernel[y][x] = 4 * oldKernel[y % oldKernel[0].length][x % oldKernel[0].length] + 3;
            }
        }
        for (int y = oldKernel[0].length; y < 2 * oldKernel[0].length; ++y) {
            for (int x = oldKernel[0].length; x < 2 * oldKernel[0].length; ++x) {
                newKernel[y][x] = 4 * oldKernel[y % oldKernel[0].length][x % oldKernel[0].length] + 1;
            }
        }
        return newKernel;
    }

    private void normalizeMap() {
        for (int i = 0; i < thresholdMap[0].length; ++i) {
            for (int j = 0; j < thresholdMap[0].length; ++j) {
                thresholdMap[i][j] = (thresholdMap[i][j] + 1) / (thresholdMap[0].length * thresholdMap[0].length) - 0.5;

            }
        }
    }


    private void orderDithering(BufferedImage oldImage, BufferedImage newImage) {
        double stepR = COLOR_AMOUNT / redPalette.length;
        double stepG = COLOR_AMOUNT / greenPalette.length;
        double stepB = COLOR_AMOUNT / bluePalette.length;
        int thresholdSize = thresholdMap[0].length;
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                int oldR = ColorUtils.parseColorRed(oldImage.getRGB(x, y));
                int oldG = ColorUtils.parseColorGreen(oldImage.getRGB(x, y));
                int oldB = ColorUtils.parseColorBlue(oldImage.getRGB(x, y));

                int newR = FilterUtils.nearestPaletteColor(oldR + stepR * thresholdMap[y % thresholdSize][x % thresholdSize], redPalette);
                int newG = FilterUtils.nearestPaletteColor(oldG + stepG * thresholdMap[y % thresholdSize][x % thresholdSize], greenPalette);
                int newB = FilterUtils.nearestPaletteColor(oldB + stepB * thresholdMap[y % thresholdSize][x % thresholdSize], bluePalette);

                newImage.setRGB(x, y, ColorUtils.getColorRGB(newR, newG, newB));
            }
        }

    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        double rSize = params[0];
        double gSize = params[1];
        double bSize = params[2];
        int kernelSize = countSize(rSize, gSize, bSize);

        if (kernelSize == 0) {
            throw new RuntimeException("Strange values for kernelSize.");
        }
        createPalette(rSize, gSize, bSize);
        thresholdMap = createThresholdMap(kernelSize);

        normalizeMap();

        orderDithering(oldImage, newImage);

        return newImage;
    }
}
