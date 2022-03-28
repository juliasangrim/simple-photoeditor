package ru.nsu.ccfit.trubitsyna.filters;

import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.utils.FilterUtils;
import ru.nsu.ccfit.trubitsyna.utils.ToolStatus;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class ContouringFilter implements IFilter {

    private final double[][] robertsKernel1;
    private final double[][] robertsKernel2;

    private final double[][] sobelsKernel1;
    private final double[][] sobelsKernel2;

    private int threshold;

    @Setter
    private ToolStatus status;

    public ContouringFilter() {
        robertsKernel1 = new double[][]{
                {0.5, 0},
                {0, -0.5}
        };
        robertsKernel2 = new double[][]{
                {0, 0.5},
                {-0.5, 0}
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


    private int conturing(BufferedImage image, int currX, int currY) {
        double resultKernelR1 = 0;
        double resultKernelG1 = 0;
        double resultKernelB1 = 0;

        double resultKernelR2 = 0;
        double resultKernelG2 = 0;
        double resultKernelB2 = 0;
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
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                    resultKernelR1 += ColorUtils.parseColorRed(image.getRGB(x, y)) * kernel1[i][j];
                    resultKernelG1 += ColorUtils.parseColorGreen(image.getRGB(x, y)) * kernel1[i][j];
                    resultKernelB1 += ColorUtils.parseColorBlue(image.getRGB(x, y)) * kernel1[i][j];

                    resultKernelR2 += ColorUtils.parseColorRed(image.getRGB(x, y)) * kernel2[i][j];
                    resultKernelG2 += ColorUtils.parseColorGreen(image.getRGB(x, y)) * kernel2[i][j];
                    resultKernelB2 += ColorUtils.parseColorBlue(image.getRGB(x, y)) * kernel2[i][j];
                }
                j = (j + 1) % kernel1[0].length;
            }
            i = (i + 1) % kernel1[0].length;
        }

        double resultR = Math.abs(resultKernelR1) + Math.abs(resultKernelR2);
        double resultG = Math.abs(resultKernelG1) + Math.abs(resultKernelG2);
        double resultB = Math.abs(resultKernelB1) + Math.abs(resultKernelB2);

        if (FilterUtils.roundColor(resultR) > threshold &&
                FilterUtils.roundColor(resultG) > threshold &&
                FilterUtils.roundColor(resultB) > threshold) {
            return ColorUtils.getColorRGB(255, 255, 255);
        } else {
            return ColorUtils.getColorRGB(0, 0, 0);
        }
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        threshold = (int) params[0];
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, conturing(oldImage, x, y));
            }
        }
        return newImage;
    }
}
