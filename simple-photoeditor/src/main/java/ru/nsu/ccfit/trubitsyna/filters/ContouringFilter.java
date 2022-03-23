package ru.nsu.ccfit.trubitsyna.filters;

import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.ColorPuller;
import ru.nsu.ccfit.trubitsyna.ToolStatus;

import java.awt.image.BufferedImage;

public class ContouringFilter implements IFilter {

    private double[][] robertsKernel1;
    private double[][] robertsKernel2;

    private double[][] sobelsKernel1;
    private double[][] sobelsKernel2;

    private int threshold;

    @Setter
    private ToolStatus status;

    public ContouringFilter() {
        robertsKernel1 = new double[][]{
                {1, 0},
                {0, -1}
        };
        robertsKernel2 = new double[][]{
                {0, 1},
                {-1, 0}
        };

        sobelsKernel1 = new double[][]{
                {-0.25, 0, 0.25},
                {-0.5, 0, 0.5},
                {-0.25, 0, 0.25}
        };
        sobelsKernel2 = new double[][]{
                {-0.25, -0.5, -0.25},
                {0, 0, 0},
                {0.25, 0.5, 0.25}
        };
    }


    private int getNewPixelColor(BufferedImage image, int currX, int currY) {
        int resultKernelR1 = 0;
        int resultKernelG1 = 0;
        int resultKernelB1 = 0;

        int resultKernelR2 = 0;
        int resultKernelG2 = 0;
        int resultKernelB2 = 0;
        //chose coordinates and  kernel depends on type of contouring
        int yBegin;
        int xBegin;
        double[][] kernel1;
        double[][] kernel2;
        if (status == ToolStatus.ROBERTS) {
            yBegin = currY;
            xBegin = currX;
            kernel1 = robertsKernel1;
            kernel2 = robertsKernel2;
        } else {
            yBegin = currY - 1;
            xBegin = currX - 1;
            kernel1 = sobelsKernel1;
            kernel2 = sobelsKernel2;
        }
        int yEnd = currY + 1;
        int xEnd = currX + 1;

        int i = 0;
        int j = 0;
        for (int y = yBegin; y <= yEnd; ++y) {
            for (int x = xBegin; x <= xEnd; ++x) {
                if (x > 0 && y > 0 && x < image.getWidth() && y < image.getHeight()) {
                    resultKernelR1 += ColorPuller.parseColorRed(image.getRGB(x, y)) * kernel1[i][j];
                    resultKernelG1 += ColorPuller.parseColorGreen(image.getRGB(x, y)) * kernel1[i][j];
                    resultKernelB1 += ColorPuller.parseColorBlue(image.getRGB(x, y)) * kernel1[i][j];

                    resultKernelR2 += ColorPuller.parseColorRed(image.getRGB(x, y)) * kernel2[i][j];
                    resultKernelG2 += ColorPuller.parseColorGreen(image.getRGB(x, y)) * kernel2[i][j];
                    resultKernelB2 += ColorPuller.parseColorBlue(image.getRGB(x, y)) * kernel2[i][j];
                }
                j = (j + 1) % kernel1[0].length;
            }
            i = (i + 1) % kernel1[0].length;
        }

        if (Math.min(Math.max(resultKernelR1 + resultKernelR2, 0), 255) > threshold &&
                Math.min(Math.max(resultKernelG1 + resultKernelG2, 0), 255) > threshold &&
                Math.min(Math.max(resultKernelB1 + resultKernelB2, 0), 255) > threshold) {
            return ColorPuller.getColorRGB(255, 255, 255);
        } else {
            return ColorPuller.getColorRGB(0, 0, 0);
        }
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage, double... params) {
        threshold = (int) params[0];
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {

                newImage.setRGB(x, y, getNewPixelColor(oldImage, x, y));
            }
        }
        return null;
    }
}
