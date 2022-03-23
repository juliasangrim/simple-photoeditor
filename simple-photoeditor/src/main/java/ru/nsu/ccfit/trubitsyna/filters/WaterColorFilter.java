package ru.nsu.ccfit.trubitsyna.filters;

import ru.nsu.ccfit.trubitsyna.ColorPuller;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WaterColorFilter implements IFilter{

    private final static int DEFAULT_SIZE = 5;

    private int[][] sharpKernel;

    public WaterColorFilter() {
        sharpKernel = new int[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
    }

    private int medianBlur(BufferedImage image, int currX, int currY) {
        int lastNotFilledElem = 0;
        int[] array = new int[DEFAULT_SIZE * DEFAULT_SIZE];
        for (int y = currY - 2; y <= currY + 2; ++y) {
            for (int x = currX - 2; x <= currX + 2; ++x) {
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                    array[lastNotFilledElem] = image.getRGB(x, y);
                    lastNotFilledElem++;
                }
            }
        }
        Arrays.sort(array);
      //  System.out.println(Arrays.toString(array));
        if (lastNotFilledElem < DEFAULT_SIZE * DEFAULT_SIZE) {
            if (lastNotFilledElem % 2 == 0) {
                return array[DEFAULT_SIZE * DEFAULT_SIZE / 2 - 1];
            } else {
                return array[DEFAULT_SIZE * DEFAULT_SIZE / 2];
            }
        } else {
            return array[DEFAULT_SIZE * DEFAULT_SIZE / 2];
        }
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage, double... params) {

        BufferedImage processedImage = ImagePanel.copyImage(oldImage);
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                processedImage.setRGB(x, y, medianBlur(oldImage, x, y));
            }
        }


        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, SharpnessFilter.sharpness(processedImage, x, y, sharpKernel));
            }
        }

        return null;
    }
}
