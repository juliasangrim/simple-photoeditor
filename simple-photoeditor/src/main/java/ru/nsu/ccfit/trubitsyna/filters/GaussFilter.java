package ru.nsu.ccfit.trubitsyna.filters;

import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.utils.ColorUtils;
import ru.nsu.ccfit.trubitsyna.view.ImagePanel;

import java.awt.image.BufferedImage;

public class GaussFilter implements IFilter {

    @Setter
    private int kernelSize;
    private double[][] kernel;

    private double gaussCoef;

    private void createKernel() {
        kernel = new double[kernelSize][kernelSize];
        double sum = 0;
        for (int y = -kernelSize / 2; y <= kernelSize / 2; ++y) {
            for (int x = -kernelSize / 2; x <= kernelSize / 2; ++x) {
                kernel[y + kernelSize / 2][x + kernelSize / 2] = (1 / (2 * Math.PI * gaussCoef * gaussCoef)) * Math.exp(-(x * x + y * y) / (2 * gaussCoef * gaussCoef));
                sum += kernel[y + kernelSize / 2][x + kernelSize / 2];
                System.out.print(kernel[y + kernelSize / 2][x + kernelSize / 2]);
            }
            System.out.println();
        }

        for (int y = 0; y < kernelSize; ++y) {
            for (int x = 0; x < kernelSize; ++x) {
                kernel[y][x] = kernel[y][x] / sum;
            }
        }
    }

    private int blur(BufferedImage image, int currX, int currY) {
        double resultR = 0;
        double resultG = 0;
        double resultB = 0;

        int i = 0;
        int j = 0;
        for (int y = currY - kernelSize / 2; y <= currY + kernelSize / 2; ++y) {
            for (int x = currX - kernelSize / 2; x <= currX + kernelSize / 2; ++x) {
                if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {

                    resultR += ColorUtils.parseColorRed(image.getRGB(x, y)) * kernel[i][j];
                    resultG += ColorUtils.parseColorGreen(image.getRGB(x, y)) * kernel[i][j];
                    resultB += ColorUtils.parseColorBlue(image.getRGB(x, y)) * kernel[i][j];

                }
                j = (j + 1) % kernel[0].length;
            }
            i = (i + 1) % kernel[0].length;
        }

        return ColorUtils.getColorRGB((int) resultR, (int) resultG, (int) resultB);
    }

    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        BufferedImage newImage = ImagePanel.copyImage(oldImage);
        kernelSize = (int) params[0];
        gaussCoef = params[1];
        System.out.println(kernelSize);
        System.out.println(gaussCoef);
        createKernel();
        for (int y = 0; y < oldImage.getHeight(); ++y) {
            for (int x = 0; x < oldImage.getWidth(); ++x) {
                newImage.setRGB(x, y, blur(oldImage, x, y));
            }
        }

        return newImage;
    }
}
